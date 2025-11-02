package com.inditex.technicaltest.features.prices.infrastructure.persistence.mapper;

import com.inditex.technicaltest.features.prices.domain.model.Price;
import com.inditex.technicaltest.features.prices.infrastructure.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PricePersistenceMapper {
    @Mapping(target = "brandId", source = "brand.id")
    Price toDomain(PriceEntity entity);
}

