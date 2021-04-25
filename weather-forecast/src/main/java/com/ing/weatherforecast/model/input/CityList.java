package com.ing.weatherforecast.model.input;

import com.google.gson.Gson;
import com.ing.weatherforecast.exception.CityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;


public class CityList {
    private City[] cityList;
    private Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(CityList.class);

    public CityList(String jsonFile) throws IOException {
        try (Reader reader = new InputStreamReader(this.getClass()
                .getResourceAsStream("/Json/" + jsonFile))) {
            // Convert JSON File to Java Object
            cityList = gson.fromJson(reader, City[].class);
        } catch(Exception e){
            throw e;
        }
    }

    public City getCityDetails(String cityName) throws CityNotFoundException {
        for (City city : cityList) {
            if(cityName.equals(city.name)){
                return city;
            }
        }
        // If the city doesn't exist
        throw new CityNotFoundException("The city " + cityName + " was not found!");
    }
}



