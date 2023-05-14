package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.services.UserService;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsController {
    private final UserService userService;

    @GetMapping
    public ModelAndView getProfileSettings() {
        ModelAndView modelAndView = new ModelAndView("/settings");
        modelAndView.addObject("user", userService.findByUsername(SecurityUtils.getAuthenticatedUsername()));
        modelAndView.addObject("authenticatedUsername", SecurityUtils.getAuthenticatedUsername());
        return modelAndView;
    }
}
