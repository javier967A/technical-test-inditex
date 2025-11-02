package com.inditex.technicaltest.features.prices.aplication.service;

import com.inditex.technicaltest.features.prices.aplication.port.out.PriceRepository;
import com.inditex.technicaltest.features.prices.domain.exception.PriceNotFoundException;
import com.inditex.technicaltest.features.prices.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PriceServiceImplTest {

    @Mock private PriceRepository repository;
    @InjectMocks private PriceServiceImpl service;

    @Test
    void returnsPriceWhenRepositoryFindsOne() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");

        Price expected = Price.builder()
                .brandId(1L).productId(35455L).priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        when(repository.findApplicablePrice(35455L, 1L, applicationDate))
                .thenReturn(Optional.of(expected));

        Price result = service.getPrice(35455L, 1L, applicationDate);
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void throwsWhenPriceNotFound() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-13T10:00:00");
        when(repository.findApplicablePrice(35455L, 1L, applicationDate)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getPrice(35455L, 1L, applicationDate))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("Price not found");
    }
}

