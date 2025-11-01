package com.inditex.technicaltest.features.prices.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Brand {
    private final Long id;
    private final String name;
}
