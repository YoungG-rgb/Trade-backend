package kg.tech.tradebackend.controllers.ext;

import kg.tech.tradebackend.controllers.BaseController;
import kg.tech.tradebackend.domain.models.ItemModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ext-api/home")
public class ExtHomePageController extends BaseController {

    private final ItemService itemService;

    @GetMapping
    public ResponseModel<List<ItemModel>> getAll(){
       return successResponse(itemService.getAll());
    }

}
