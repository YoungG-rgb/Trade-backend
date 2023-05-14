package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import kg.tech.tradebackend.mappers.UserMapper;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    EmailSenderService emailSenderService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

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
    public UserModel update(UserModel userModel) throws TradeException {
        if (userModel.getId() == null) throw new TradeException ("ID is null");

        User user = userRepository.save(userMapper.toEntity(userModel));
        return userMapper.toModel(user);
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
}
