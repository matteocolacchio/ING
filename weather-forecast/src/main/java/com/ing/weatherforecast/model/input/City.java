package com.ing.weatherforecast.model.input;

public class City {
    public int id;
    public String name;
    public String state;
    public String country;
    public Coord coord;

    @Override
    public String toString() {
        return "(Id: " + id + ", Name: " + name + ", State: " + state + ", Country: " + country + ", Coord: " + coord.toString() + ")";
    }
}


