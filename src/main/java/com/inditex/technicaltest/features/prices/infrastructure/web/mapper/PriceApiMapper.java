package com.inditex.technicaltest.features.prices.infrastructure.web.mapper;

import com.inditex.technicaltest.features.prices.domain.model.Price;
import com.inditex.technicaltest.features.prices.infrastructure.web.dto.PriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceApiMapper {
    PriceResponse toResponse(Price domain);
}
