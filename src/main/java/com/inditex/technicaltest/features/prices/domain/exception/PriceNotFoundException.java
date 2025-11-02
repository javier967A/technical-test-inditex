package com.inditex.technicaltest.features.prices.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) { super(message); }
}
