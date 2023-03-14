package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.models.ItemFilterPattern;
import kg.tech.tradebackend.domain.models.ItemModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController extends BaseController {

    private final ItemService itemService;

    @PostMapping("/filter")
    public ResponseModel<Page<ItemModel>> getItems(@RequestBody ItemFilterPattern searchPattern, Pageable pageable) {
        return successResponse(itemService.filter(searchPattern, pageable));
    }

    @GetMapping("/{id}")
    public ResponseModel<ItemModel> getById(@PathVariable("id") Long id) throws Exception {
        return successResponse(itemService.findById(id));
    }

    @PostMapping
    public ResponseModel<ItemModel> save(@RequestBody ItemModel itemModel) {
        return successResponse(itemService.save(itemModel));
    }

    @DeleteMapping("/{id}")
    public ResponseModel<String> delete(@PathVariable Long id) throws Exception {
        itemService.delete(id);
        return successResponse("DELETED");
    }
}
