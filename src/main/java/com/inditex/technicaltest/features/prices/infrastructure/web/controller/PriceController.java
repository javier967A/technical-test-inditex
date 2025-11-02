package com.inditex.technicaltest.features.prices.infrastructure.web.controller;

import com.inditex.technicaltest.features.prices.aplication.port.in.PriceUseCase;
import com.inditex.technicaltest.features.prices.infrastructure.web.dto.PriceRequest;
import com.inditex.technicaltest.features.prices.infrastructure.web.dto.PriceResponse;
import com.inditex.technicaltest.features.prices.infrastructure.web.mapper.PriceApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceUseCase useCase;
    private final PriceApiMapper mapper;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam Long productId,
            @RequestParam Long brandId
    ) {
        var price = useCase.getPrice(productId, brandId, applicationDate);
        return ResponseEntity.ok(mapper.toResponse(price));
    }
}
