package kg.tech.tradebackend.controllers.ext;

import kg.tech.tradebackend.controllers.BaseController;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ext-api/users")
@RequiredArgsConstructor
public class UserExtRestController extends BaseController {

    private final UserService userService;

    @PostMapping
    public ResponseModel<UserModel> register(@RequestBody UserModel userModel) {
        return successResponse(userService.save(userModel));
    }
}
