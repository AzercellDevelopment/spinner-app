package com.spinnerapp.controller;

import com.spinnerapp.model.dto.GiftRequestDto;
import com.spinnerapp.model.dto.GiftResponseDto;
import com.spinnerapp.model.entity.Gift;
import com.spinnerapp.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;

    @GetMapping
    public ResponseEntity<List<GiftResponseDto>> getAllGifts(Principal principal) {
        List<GiftResponseDto> gifts = giftService.getAllGiftsByUser(Long.valueOf(principal.getName()));
        return ResponseEntity.ok(gifts);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<GiftResponseDto>> getAllActiveGifts(Principal principal) {
        List<GiftResponseDto> allActiveGifts = giftService.getAllActiveGifts(Long.valueOf(principal.getName()));
        return ResponseEntity.ok(allActiveGifts);
    }

    @PostMapping
    public ResponseEntity<GiftResponseDto> addGift(Principal principal,
                                                   @RequestBody GiftRequestDto giftRequestDto) {
        GiftResponseDto giftResponseDto = giftService.addGift(Long.valueOf(principal.getName()), giftRequestDto);
        return ResponseEntity.ok(giftResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftResponseDto> updateGift(@PathVariable Long id,
                                                      @RequestBody GiftRequestDto giftRequestDto,
                                                      Principal principal) {
        GiftResponseDto giftResponseDto = giftService.updateGift(id,Long.valueOf(principal.getName()), giftRequestDto);
        return ResponseEntity.ok(giftResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GiftResponseDto> deleteGift(@PathVariable Long id) {
        GiftResponseDto giftResponseDto = giftService.deleteGift(id);
        return ResponseEntity.ok(giftResponseDto);
    }
}
