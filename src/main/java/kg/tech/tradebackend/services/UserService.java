package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

    UserModel findById(Long id) throws Exception;
    Page<UserModel> filter(UserFilterPattern userFilterPattern);
    UserModel save(UserModel user);
    UserRegisterModel save(UserRegisterModel user) throws Exception;
    UserModel update(UserModel userModel) throws Exception;
    UserModel adminUpdate(UserModel userModel) throws Exception;
    void delete(Long id) throws Exception;



}
