package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

    UserModel findById(Long id) throws Exception;
    Page<UserModel> filter(UserFilterPattern userFilterPattern, Pageable pageable);
    UserModel save(UserModel user);
    UserRegisterModel save(UserRegisterModel user) throws TradeException;
    UserModel update(UserModel userModel) throws Exception;
    void delete(Long id) throws Exception;



}
