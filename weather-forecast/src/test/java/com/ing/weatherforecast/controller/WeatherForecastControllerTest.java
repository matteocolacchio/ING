package com.ing.weatherforecast.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class WeatherForecastControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getWeatherForecastOkTest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/WeatherForecast")
                .param("city", "Milano")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getWeatherForecast404Test() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/WeatherForecast")
                .param("city", "Milanoxxxx")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getWeatherForecast400Test() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/WeatherForecast")
                .param("citys", "Milano")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}