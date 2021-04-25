package com.ing.weatherforecast.model.input;

import com.google.gson.Gson;
import static org.junit.jupiter.api.Assertions.*;
import com.ing.weatherforecast.exception.CityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


class CityListTest {
    private Gson gson = new Gson();
    private Properties properties;
    private CityList cityList;


    @BeforeEach
    void init() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        properties = new Properties();
        properties.load(is);
        cityList = new CityList(properties.getProperty("cityListJsonPath"));
    }

    @Test
    void getCityDetailsTest() throws IOException, CityNotFoundException {
        City city = cityList.getCityDetails("Milano");
        String cityJson = gson.toJson(city);
        String expectedString = "{\"id\":6542283,\"name\":\"Milano\",\"state\":\"\",\"country\":\"IT\",\"coord\":{\"lon\":9.19199,\"lat\":45.464161}}";
        assertEquals(cityJson, expectedString);
    }
}