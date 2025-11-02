package com.inditex.technicaltest.features.prices.aplication.port.out;

import com.inditex.technicaltest.features.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
