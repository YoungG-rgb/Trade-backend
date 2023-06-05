package kg.tech.tradebackend.controllers.ext;

import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.services.UserService;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("CartExtApi")
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final UserService userService;

    @GetMapping
    public String getOrders(Model model){
        UserModel user = userService.findByUsername(SecurityUtils.getAuthenticatedUsername());
        model.addAttribute("user", user);
        model.addAttribute("isAnonymous", SecurityUtils.isAnonymous());

        return "cart";
    }

}
