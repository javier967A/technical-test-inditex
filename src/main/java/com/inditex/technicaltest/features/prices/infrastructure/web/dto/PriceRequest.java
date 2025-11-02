package com.inditex.technicaltest.features.prices.infrastructure.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PriceRequest {

    private Long productId;
    private Long brandId;
    private LocalDateTime applicationDate;
}
