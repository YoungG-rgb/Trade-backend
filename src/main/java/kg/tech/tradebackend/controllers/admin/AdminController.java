package kg.tech.tradebackend.controllers.admin;

import kg.tech.tradebackend.domain.enums.Color;
import kg.tech.tradebackend.domain.filterPatterns.CouponFilterPattern;
import kg.tech.tradebackend.domain.filterPatterns.ItemFilterPattern;
import kg.tech.tradebackend.domain.filterPatterns.RoleFilterPattern;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.services.RoleService;
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
    private final RoleService roleService;

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("authenticatedUsername", SecurityUtils.getAuthenticatedUsername());
        model.addAttribute("userFilterPattern", new UserFilterPattern());
        model.addAttribute("roleFilterPattern", new RoleFilterPattern());
        model.addAttribute("itemFilterPattern", new ItemFilterPattern());
        model.addAttribute("couponFilterPattern", new CouponFilterPattern());
        model.addAttribute("AllRoles", roleService.getRoles());
        model.addAttribute("allColors", Color.values());
        return "dashboard";
    }
}
