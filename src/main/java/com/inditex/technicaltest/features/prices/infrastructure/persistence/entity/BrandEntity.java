package com.inditex.technicaltest.features.prices.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "brands")
@Getter
@Setter
public class BrandEntity {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;
}

