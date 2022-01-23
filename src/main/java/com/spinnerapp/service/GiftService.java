package com.spinnerapp.service;

import com.spinnerapp.mapper.GiftMapper;
import com.spinnerapp.model.dto.GiftRequestDto;
import com.spinnerapp.model.dto.GiftResponseDto;
import com.spinnerapp.model.entity.Gift;
import com.spinnerapp.model.entity.User;
import com.spinnerapp.repository.GiftRepository;
import com.spinnerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftService {

    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final GiftMapper giftMapper;

    public List<GiftResponseDto> getAllGiftsByUser(Long userId) {
        List<Gift> gifts = giftRepository.findAllByUser(userId);
        return giftMapper.entitiesToDto(gifts);
    }

    public List<GiftResponseDto> getAllActiveGifts(Long userId) {
        List<Gift> activeGifts = giftRepository.findByUserAndStatusTrue(userId);
        return giftMapper.entitiesToDto(activeGifts);
    }

    public GiftResponseDto addGift(Long userId, GiftRequestDto giftRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        Gift gift = giftMapper.dtoToEntity(giftRequestDto);
        gift.setUser(user);
        giftRepository.save(gift);
        GiftResponseDto giftResponseDto = giftMapper.entityToDto(gift);
        return giftResponseDto;

    }

    public GiftResponseDto updateGift(Long id, Long userId, GiftRequestDto giftRequestDto) {
        Gift gift = giftRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        if (gift != null) {
            gift = giftMapper.dtoToEntity(giftRequestDto);
            gift.setId(id);
            User user = userRepository.findById(userId).orElseThrow(() -> {
                throw new EntityNotFoundException();
            });
            gift.setUser(user);
        }
        giftRepository.save(gift);
        GiftResponseDto giftResponseDto = giftMapper.entityToDto(gift);
        return giftResponseDto;
    }

    public GiftResponseDto deleteGift(Long id) {
        Gift gift = giftRepository.findById(id).orElseThrow(
                () -> {
                    throw new EntityNotFoundException();
                }
        );
        giftRepository.delete(gift);
        GiftResponseDto giftResponseDto = giftMapper.entityToDto(gift);
        return giftResponseDto;
    }
}
