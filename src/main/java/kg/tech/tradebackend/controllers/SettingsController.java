package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.services.UserService;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsController {
    private final UserService userService;

    @GetMapping
    public String getProfileSettings(Model model) {
        model.addAttribute("user", userService.findByUsername(SecurityUtils.getAuthenticatedUsername()));
        model.addAttribute("authenticatedUsername", SecurityUtils.getAuthenticatedUsername());
        return "settings";
    }
}
