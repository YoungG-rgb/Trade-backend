package kg.tech.tradebackend.controllers.admin;

import kg.tech.tradebackend.controllers.BaseController;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.data_tables.DataTablePage;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/filter")
    public ResponseEntity<DataTablePage<UserModel>> getAll(@RequestBody UserFilterPattern userFilterPattern) {
        return ResponseEntity.ok(new DataTablePage<>(userFilterPattern, userService.filter(userFilterPattern)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserModel> save(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(userService.save(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        userService.delete(id);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<UserModel> adminUpdate(@RequestBody UserModel userModel) throws Exception {
        return ResponseEntity.ok(userService.adminUpdate(userModel));
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserModel userModel) throws Exception {
        return ResponseEntity.ok(userService.update(userModel));
    }
}
