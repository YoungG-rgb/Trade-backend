package kg.tech.tradebackend.controllers.admin;

import kg.tech.tradebackend.domain.filterPatterns.RoleFilterPattern;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("authenticatedUsername", SecurityUtils.getAuthenticatedUsername());
        model.addAttribute("userFilterPattern", new UserFilterPattern());
        model.addAttribute("roleFilterPattern", new RoleFilterPattern());
        return "dashboard";
    }
}
