package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.exceptions.GatewayException;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.mappers.UserMapper;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.repositories.UserRepository;
import kg.tech.tradebackend.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserModel> getAll() {
        return userRepository.findAll().stream().map(userMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public UserModel findById(Long id) throws GatewayException {
        Optional<User> user = userRepository.findById(id);
        return user
                .map(userMapper::toModel)
                .orElseThrow(() -> new GatewayException("USER_NOT_FOUND"));
    }

    @Override
    public UserModel save(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userMapper.toEntity(user));
        log.info("UserServiceImpl.save::user saved");
        return user;
    }

    @Override
    public UserModel update(UserModel userModel) throws GatewayException {
        if (userModel.getId() == null) throw new GatewayException("ID is null");
        return this.save(userModel);
    }

    @Override
    public void delete(Long id) throws GatewayException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) throw new GatewayException("USER IS ALREADY DELETED");
        userRepository.delete(userOptional.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("USERNAME IS NULL"));
    }
}
