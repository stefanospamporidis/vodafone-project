package com.example.vodafone.exception;

public class PointOfInterestNotFoundException extends RuntimeException {
    public PointOfInterestNotFoundException(String message) {
        super(message);
    }
}