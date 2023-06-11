package kg.tech.tradebackend.controllers.ext;

import kg.tech.tradebackend.controllers.BaseController;
import kg.tech.tradebackend.domain.filterPatterns.ItemFilterPattern;
import kg.tech.tradebackend.domain.models.ItemModel;
import kg.tech.tradebackend.domain.models.data_tables.DataTablePage;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ext-api/items")
@RequiredArgsConstructor
public class ItemController extends BaseController {

    private final ItemService itemService;

    @PostMapping("/filter")
    public DataTablePage<ItemModel> getItems(@RequestBody ItemFilterPattern searchPattern) {
        return new DataTablePage<>(searchPattern, itemService.filter(searchPattern));
    }

}
