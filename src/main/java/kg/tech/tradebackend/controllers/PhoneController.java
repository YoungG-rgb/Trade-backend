package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.models.PhoneModel;
import kg.tech.tradebackend.services.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/phones")
public class PhoneController {
    private final PhoneService phoneService;

    @PostMapping("/{userId}")
    public ResponseEntity<PhoneModel> save(@PathVariable Long userId, @RequestBody PhoneModel phoneModel) {
        return ResponseEntity.ok(phoneService.save(phoneModel.getNumber(), userId));
    }
}
