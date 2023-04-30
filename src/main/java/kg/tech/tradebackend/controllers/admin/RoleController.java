package kg.tech.tradebackend.controllers.admin;

import kg.tech.tradebackend.domain.filterPatterns.RoleFilterPattern;
import kg.tech.tradebackend.domain.models.RoleModel;
import kg.tech.tradebackend.domain.models.data_tables.DataTablePage;
import kg.tech.tradebackend.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/filter")
    public ResponseEntity<DataTablePage<RoleModel>> getAll(@RequestBody RoleFilterPattern roleFilterPattern) {
        return ResponseEntity.ok(new DataTablePage<>(roleFilterPattern, roleService.filter(roleFilterPattern)));
    }

    @GetMapping
    public ResponseEntity<List<RoleModel>> getRolesWithoutPageable(){
        return ResponseEntity.ok(roleService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleModel> findById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleModel> save(@RequestBody RoleModel userModel) {
        return ResponseEntity.ok(roleService.save(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        roleService.delete(id);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<RoleModel> update(@RequestBody RoleModel userModel) throws Exception {
        return ResponseEntity.ok(roleService.update(userModel));
    }
}
