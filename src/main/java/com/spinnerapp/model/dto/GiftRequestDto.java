package com.spinnerapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftRequestDto {
    private String name;
    private Long userId;
    private boolean status;
}
