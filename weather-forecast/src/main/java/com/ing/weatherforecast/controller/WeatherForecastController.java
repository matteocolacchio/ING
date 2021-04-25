package com.ing.weatherforecast.controller;

import com.ing.weatherforecast.exception.CityNotFoundException;
import com.ing.weatherforecast.model.input.City;
import com.ing.weatherforecast.model.input.CityList;
import com.ing.weatherforecast.model.openweathermapResponse.OpenweathermapResponse;
import com.ing.weatherforecast.openweathermapAPI.OpenweathermapAPI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;


@RestController
public class WeatherForecastController {
    private final Logger logger = LoggerFactory.getLogger(WeatherForecastController.class);
    private Properties properties;
    private CityList cityList;


    public WeatherForecastController(){
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(is);
            cityList = new CityList(properties.getProperty("cityListJsonPath"));
        }catch(Exception e){
            logger.error("An Error has occurred while init the Weather Forecast Controller!", e);
        }
    }

    @Operation(summary = "Get 2 days weather forecast for the input city")
    @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200", description = "Weather forecast request has been succeed",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(
                                                implementation = JsonResponse.class
                                        ),
                                        examples = {
                                                @ExampleObject(
                                                    name = "Status Code 200",
                                                    summary = "Weather forecast request has been succeed",
                                                    value = "{\"timestamp\":\"1990-09-27T20:10:07.671+02:00\",\"status\":\"200\",\"error\":\"None\",\"message\":{},\"path\":\"/WeatherForecast\"}"
                                                )
                                        }
                                )
                        }
                ),
                @ApiResponse(
                        responseCode = "400", description = "Invalid city parameter supplied",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(
                                                implementation = JsonResponse.class
                                        ),
                                        examples = {
                                                @ExampleObject(
                                                        name = "Status Code 400",
                                                        summary = "Invalid city parameter supplied",
                                                        value = "{\"timestamp\":\"1990-09-27T20:10:07.671+02:00\",\"status\":\"400\",\"error\":\"Bad Request\",\"message\":{},\"path\":\"/WeatherForecast\"}"
                                                )
                                        }
                                ),

                        }
                ),
                @ApiResponse(
                        responseCode = "404", description = "The searched city was not found",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(
                                                implementation = JsonResponse.class
                                        ),
                                        examples = {
                                                @ExampleObject(
                                                        name = "Status Code 404",
                                                        summary = "The searched city was not found",
                                                        value = "{\"timestamp\":\"1990-09-27T20:10:07.671+02:00\",\"status\":\"404\",\"error\":\"The city Milanoxxx was not found!\",\"message\":{},\"path\":\"/WeatherForecast\"}"
                                                )
                                        }
                                )
                        }
                )
        }
    )
    @GetMapping(path = "/WeatherForecast" , produces = "application/json; charset=UTF-8")
    public ResponseEntity<JsonResponse> getWeatherForecast(@Parameter(description = "The name of the city to be searched") @RequestParam(name = "city") String cityName){
        try {
            City cityDetails = cityList.getCityDetails(cityName);
            String endPoint = properties.getProperty("openweathermapEndPoint");
            String apiKey = properties.getProperty("openweathermapApiKey");
            OpenweathermapAPI openweathermapAPI = new OpenweathermapAPI(endPoint, "metric", apiKey);
            OpenweathermapResponse openweathermapResponse = openweathermapAPI.getWeatherForecast48(cityDetails.name);
            return new ResponseEntity<>(new JsonResponse("200", "None", openweathermapResponse, "/WeatherForecast"), null, HttpStatus.OK);
        }catch (CityNotFoundException e){
            logger.error("The city '" + cityName + "' was not found!", e);
            return new ResponseEntity<>(new JsonResponse("404", e.getMessage(), "", "/WeatherForecast"), null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("An Error has occurred in the Weather Forecast Controller!", e);
            return new ResponseEntity<>(new JsonResponse("500", e.getMessage(), "", "/WeatherForecast"), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


class JsonResponse implements Serializable{
    private String timestamp;
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private String status;
    private String error;
    private Object message;
    private String path;


    public JsonResponse(String status, String error, Object message, String path) {
        this.timestamp = sdf2.format(new Timestamp(System.currentTimeMillis()));
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static SimpleDateFormat getSdf2() {
        return sdf2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}