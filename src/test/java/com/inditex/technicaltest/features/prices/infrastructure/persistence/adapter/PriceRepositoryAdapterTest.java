package com.inditex.technicaltest.features.prices.infrastructure.persistence.adapter;

import com.inditex.technicaltest.features.prices.infrastructure.persistence.mapper.PricePersistenceMapperImpl;
import com.inditex.technicaltest.features.prices.infrastructure.persistence.repository.PriceJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import({PriceRepositoryAdapter.class, PricePersistenceMapperImpl.class})
@ActiveProfiles("test")
class PriceRepositoryAdapterTest {

    @Autowired private PriceRepositoryAdapter adapter;

    @Autowired private PriceJpaRepository jpaRepository; // sanity check

    @Test
    void findsTopPriorityPriceWhenTwoOverlap() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T16:00:00");
        var maybePrice = adapter.findApplicablePrice(35455L, 1L, date);
        assertThat(maybePrice).isPresent();
        var price = maybePrice.get();
        assertThat(price.getPriceList()).isEqualTo(2);
        assertThat(price.getPrice()).isEqualByComparingTo("25.45");
    }

    @Test
    void returnsEmptyWhenNoPriceApplies() {
        LocalDateTime date = LocalDateTime.parse("2020-06-13T10:00:00");
        var maybePrice = adapter.findApplicablePrice(35455L, 1L, date);
        assertThat(maybePrice).isEmpty();
    }
}

