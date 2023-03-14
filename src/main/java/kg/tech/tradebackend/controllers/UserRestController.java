package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserRestController extends BaseController {

    private final UserService userService;

    @GetMapping
    public ResponseModel<List<UserModel>> getAll(){
        return successResponse(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseModel<UserModel> findById(@PathVariable("id") Long id) throws Exception {
        return successResponse(userService.findById(id));
    }

    @PostMapping
    public ResponseModel<UserModel> register(@RequestBody UserModel userModel) {
        return successResponse(userService.save(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseModel<String> delete(@PathVariable Long id) throws Exception {
        userService.delete(id);
        return successResponse("OK");
    }

    @PutMapping
    public ResponseModel<UserModel> update(@RequestBody UserModel userModel) throws Exception {
        return successResponse(userService.update(userModel));
    }
}
