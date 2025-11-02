package com.inditex.technicaltest.features.prices.infrastructure.persistence.repository;

import com.inditex.technicaltest.features.prices.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
            SELECT p FROM PriceEntity p
             WHERE p.productId = :productId
               AND p.brand.id = :brandId
               AND :applicationDate BETWEEN p.startDate AND p.endDate
             ORDER BY p.priority DESC
            """)
    List<PriceEntity> findApplicablePrices(@Param("productId") Long productId,
                                           @Param("brandId") Long brandId,
                                           @Param("applicationDate") LocalDateTime applicationDate);
}

