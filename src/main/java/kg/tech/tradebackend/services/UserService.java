package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService{

    UserModel findById(Long id) throws Exception;
    Page<UserModel> filter(UserFilterPattern userFilterPattern);
    UserModel save(UserModel user);
    UserRegisterModel save(UserRegisterModel user) throws Exception;
    String update(UserModel userModel) throws Exception;
    UserModel updateWithoutEncode(UserModel userModel) throws TradeException;
    UserModel adminUpdate(UserModel userModel) throws Exception;
    void delete(Long id) throws Exception;

    UserModel findByUsername(String username);
    List<CouponModel> findCouponsByUserId(Long userId) throws TradeException;
    CouponModel findByIdAndApplyCoupon(Long userId, String uuid) throws TradeException;

}
