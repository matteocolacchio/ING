package com.ing.weatherforecast.exception;

public class CityNotFoundException extends Exception {
    public CityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}