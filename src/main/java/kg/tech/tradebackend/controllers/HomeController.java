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
@RequestMapping("/index")
public class HomeController {


    @GetMapping
    public String home(Model model){
        model.addAttribute("isAnonymous", SecurityUtils.isAnonymous());
        return "index";
    }
}
