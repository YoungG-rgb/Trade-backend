package kg.tech.tradebackend.controllers.admin;

import kg.tech.tradebackend.domain.filterPatterns.ItemFilterPattern;
import kg.tech.tradebackend.domain.models.ItemModel;
import kg.tech.tradebackend.domain.models.data_tables.DataTablePage;
import kg.tech.tradebackend.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "AdminsItemController")
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/filter")
    public ResponseEntity<DataTablePage<ItemModel>> getItems(@RequestBody ItemFilterPattern searchPattern) {
        return ResponseEntity.ok(new DataTablePage<>(searchPattern, itemService.filter(searchPattern)));
    }

    @PostMapping
    public ResponseEntity<ItemModel> save(@RequestBody ItemModel itemModel) {
        return ResponseEntity.ok(itemService.save(itemModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        itemService.delete(id);
        return ResponseEntity.ok("DELETED");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemModel> getById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PutMapping
    public ResponseEntity<ItemModel> update(@RequestBody ItemModel itemModel) throws Exception {
        return ResponseEntity.ok(itemService.update(itemModel));
    }
}
