package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/filter")
    public ResponseModel<Page<UserModel>> getAll(@RequestBody UserFilterPattern userFilterPattern, Pageable pageable){
        return successResponse(userService.filter(userFilterPattern, pageable));
    }

    @GetMapping("/{id}")
    public ResponseModel<UserModel> findById(@PathVariable("id") Long id) throws Exception {
        return successResponse(userService.findById(id));
    }

    @PostMapping
    public ResponseModel<UserModel> register(@RequestBody UserModel userModel) {
        return successResponse(userService.save(userModel));
    }

    @PostMapping("/register")
    public ResponseModel<UserRegisterModel> register(@RequestBody UserRegisterModel userModel) {
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
