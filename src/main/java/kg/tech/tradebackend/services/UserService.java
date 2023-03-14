package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.models.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService{

    UserModel findById(Long id) throws Exception;
    List<UserModel> getAll();
    UserModel save(UserModel user);
    UserModel update(UserModel userModel) throws Exception;
    void delete(Long id) throws Exception;



}
