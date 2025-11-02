package com.inditex.technicaltest.features.prices.aplication.port.in;

import com.inditex.technicaltest.features.prices.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceUseCase {
    Price getPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
