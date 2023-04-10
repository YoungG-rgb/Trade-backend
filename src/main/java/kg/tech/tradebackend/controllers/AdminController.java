package kg.tech.tradebackend.controllers;

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
        return "dashboard";
    }
}
