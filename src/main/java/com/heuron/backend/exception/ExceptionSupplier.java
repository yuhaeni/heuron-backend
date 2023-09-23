package com.heuron.backend.exception;

import java.util.function.Supplier;

public class ExceptionSupplier extends RuntimeException {

    public Supplier<ExceptionSupplier> supplier(String message) {
        return () -> new ExceptionSupplier();
    }
}