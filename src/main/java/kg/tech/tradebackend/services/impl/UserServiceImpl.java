package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Role;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import kg.tech.tradebackend.mappers.UserMapper;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.repositories.RoleRepository;
import kg.tech.tradebackend.repositories.UserRepository;
import kg.tech.tradebackend.services.UserService;
import kg.tech.tradebackend.specifications.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public Page<UserModel> filter(UserFilterPattern userFilterPattern, Pageable pageable) {
        return userRepository.findAll( new UserSpecification(userFilterPattern), pageable ).map(userMapper::toModel);
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
    public UserRegisterModel save(UserRegisterModel userModel) throws TradeException {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        User user = userRepository.save(userMapper.toEntity(userModel));

        // save role USER_ROLE
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new TradeException("В базе нет роли для юзера")));
        user.setRoles(roles);
        userRepository.save(user);

        log.info("UserServiceImpl.save::new user saved");
        return userMapper.toRegisterModel(user);
    }

    @Override
    public UserModel update(UserModel userModel) throws TradeException {
        if (userModel.getId() == null) throw new TradeException ("ID is null");
        return this.save(userModel);
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
}
