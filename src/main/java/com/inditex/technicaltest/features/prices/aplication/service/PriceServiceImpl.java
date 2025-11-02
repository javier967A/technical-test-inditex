package com.inditex.technicaltest.features.prices.aplication.service;

import com.inditex.technicaltest.features.prices.aplication.port.in.PriceUseCase;
import com.inditex.technicaltest.features.prices.aplication.port.out.PriceRepository;
import com.inditex.technicaltest.features.prices.domain.exception.PriceNotFoundException;
import com.inditex.technicaltest.features.prices.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price getPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceRepository.findApplicablePrice(productId, brandId, applicationDate)
                .orElseThrow(() -> new PriceNotFoundException("Price not found"));
    }
}
