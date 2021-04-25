package com.ing.weatherforecast.model.input;

public class Coord {
    public double lon;
    public double lat;

    @Override
    public String toString() {
        return "(Latitude: " + lat + ", Longitude: " + lon + ")";
    }
}
