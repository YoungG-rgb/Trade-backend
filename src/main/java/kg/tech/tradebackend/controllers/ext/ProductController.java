package kg.tech.tradebackend.controllers.ext;

import kg.tech.tradebackend.domain.enums.Color;
import kg.tech.tradebackend.domain.filterPatterns.ItemFilterPattern;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public String productsPage(Model model){
        model.addAttribute("isAnonymous", SecurityUtils.isAnonymous());
        model.addAttribute("itemFilterPattern", new ItemFilterPattern());
        model.addAttribute("allColors", Color.values());
        return "products";
    }
}
