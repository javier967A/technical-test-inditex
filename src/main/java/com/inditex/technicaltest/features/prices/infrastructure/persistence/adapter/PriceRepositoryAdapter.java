package com.inditex.technicaltest.features.prices.infrastructure.persistence.adapter;



import com.inditex.technicaltest.features.prices.aplication.port.out.PriceRepository;
import com.inditex.technicaltest.features.prices.domain.model.Price;
import com.inditex.technicaltest.features.prices.infrastructure.persistence.mapper.PricePersistenceMapper;
import com.inditex.technicaltest.features.prices.infrastructure.persistence.repository.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

    private final PriceJpaRepository jpa;
    private final PricePersistenceMapper mapper;

    @Override
    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return jpa.findApplicablePrices(productId, brandId, applicationDate)
                .stream()
                .findFirst()
                .map(mapper::toDomain);
    }
}

