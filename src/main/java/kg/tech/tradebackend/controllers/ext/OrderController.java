package kg.tech.tradebackend.controllers.ext;

import kg.tech.tradebackend.domain.filterPatterns.OrderFilterPattern;
import kg.tech.tradebackend.services.UserService;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ExtOrderController")
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final UserService userService;

    @GetMapping
    public String renderOrders(Model model) {
        model.addAttribute("isAnonymous", SecurityUtils.isAnonymous());
        model.addAttribute("userId", userService.findByUsername(SecurityUtils.getAuthenticatedUsername()).getId());
        model.addAttribute("filterPattern", new OrderFilterPattern());
        return "orders";
    }
}
