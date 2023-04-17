package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.models.ImageModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController extends BaseController {

    private final ImageService imageService;

    @PostMapping
    public ResponseModel<ImageModel> create(@RequestBody ImageModel imageModel) {
        return successResponse(imageService.save(imageModel));
    }

    @DeleteMapping
    public ResponseModel<Boolean> delete(@RequestParam("id") Long id) throws Exception {
        imageService.delete(id);
        return successResponse(true);
    }
}
