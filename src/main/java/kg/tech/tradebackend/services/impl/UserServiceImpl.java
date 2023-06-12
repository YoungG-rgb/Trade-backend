package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Coupon;
import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import kg.tech.tradebackend.mappers.CouponMapper;
import kg.tech.tradebackend.mappers.UserMapper;
import kg.tech.tradebackend.repositories.CouponRepository;
import kg.tech.tradebackend.repositories.OrderRepository;
import kg.tech.tradebackend.repositories.UserRepository;
import kg.tech.tradebackend.services.EmailSenderService;
import kg.tech.tradebackend.services.UserService;
import kg.tech.tradebackend.specifications.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    static String HISTORY_PLACEHOLDER = "Вы сэкономили %s с, сумма заказа %s с <br>";

    UserRepository userRepository;
    EmailSenderService emailSenderService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    CouponMapper couponMapper;
    CouponRepository couponRepository;
    OrderRepository orderRepository;

    @Override
    public Page<UserModel> filter(UserFilterPattern userFilterPattern) {
        Page<User> userPages = userRepository.findAll(new UserSpecification(userFilterPattern), userFilterPattern.toPageRequest());

        return new PageImpl<>(
                userPages.stream().map(userMapper::toModel).toList(),
                userFilterPattern.toPageRequest(),
                userPages.getTotalPages()
        );
    }

    @Override
    public UserModel findById(Long id) throws TradeException {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toModel).orElseThrow(() -> new TradeException("USER_NOT_FOUND"));
    }

    @Override
    public UserModel save(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        User user = userRepository.save(userMapper.toEntity(userModel));
        log.info("UserServiceImpl.save::user saved");
        return userMapper.toModel(user);
    }

    @Override
    public UserRegisterModel save(UserRegisterModel userModel) throws Exception {
        String decodedPassword = userModel.getPassword();
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        log.info("UserServiceImpl.save::new user saved");
        UserRegisterModel registerModel = userMapper.toRegisterModel(
                userRepository.save(userMapper.toEntity(userModel))
        );
        emailSenderService.send(userModel.getEmail(), decodedPassword, userModel.getUsername());
        return registerModel;
    }

    @Override
    public String update(UserModel userModel) throws TradeException {
        if (userModel.getId() == null) throw new TradeException ("ID is null");

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        Optional<User> user = userRepository.findById(userModel.getId());
        user.ifPresent(u -> {
            userMapper.update(u, userModel);
            userRepository.save(u);
        });
        return "OK";
    }

    public UserModel updateWithoutEncode(UserModel userModel) throws TradeException {
        if (userModel.getId() == null) throw new TradeException ("ID is null");
        User returnedUserModel = null;

        Optional<User> user = userRepository.findById(userModel.getId());
        if (user.isPresent()) {
            userMapper.update(user.get(), userModel);
            returnedUserModel = userRepository.save(user.get());
        }
        return userMapper.toModel(returnedUserModel);
    }
    @Override
    public UserModel adminUpdate(UserModel userModel) throws Exception {
        User userFromDb = userRepository.findById(userModel.getId()).orElseThrow(() -> new TradeException("ID is null"));
        userMapper.adminUpdate(userFromDb, userModel);
        return userMapper.toModel(userRepository.save(userFromDb));
    }

    @Override
    public void delete(Long id) throws TradeException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) throw new TradeException("USER IS ALREADY DELETED");
        userRepository.delete(userOptional.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("USERNAME IS NULL"));
    }

    @Override
    public UserModel findByUsername(String username) {
        return userMapper.toModel(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("USERNAME IS NULL"))
        );
    }

    @Override
    public List<CouponModel> findCouponsByUserId(Long userId) throws TradeException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new TradeException("USER NOT FOUND"))
                .getCoupons()
                .stream()
                .filter(Coupon::isValid)
                .map(couponMapper::toModel)
                .toList();
    }

    @Override
    public CouponModel findByIdAndApplyCoupon(Long userId, String uuid) throws TradeException {
        Coupon coupon = couponRepository.findByUuid(uuid).orElseThrow(() -> new TradeException("COUPON NOT FOUND"));
        coupon.setValid(false);

        Order order = orderRepository.findByUserIdAndStatusIs(userId, OrderStatus.START);
        if (order == null) throw new TradeException("Ошибка, в корзине нет товаров");
        order.setTotal(order.getTotal().subtract(coupon.getBonus()));
        if (order.getHistory() != null) {
            order.setHistory(order.getHistory() + String.format(HISTORY_PLACEHOLDER, coupon.getBonus(), order.getTotal()));
        } else {
            order.setHistory(String.format(HISTORY_PLACEHOLDER, coupon.getBonus(), order.getTotal()));
        }

        orderRepository.save(order);
        couponRepository.save(coupon);

        return couponMapper.toModel(coupon);

    }
}
