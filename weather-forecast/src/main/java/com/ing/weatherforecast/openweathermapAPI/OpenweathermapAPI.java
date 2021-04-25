package com.ing.weatherforecast.openweathermapAPI;

import com.google.gson.Gson;
import com.ing.weatherforecast.exception.OpenweathermapException;
import com.ing.weatherforecast.http.Http;
import com.ing.weatherforecast.model.openweathermapResponse.OpenweathermapResponse;
import org.apache.http.client.utils.URIBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class OpenweathermapAPI {
    private final Http http = new Http();
    private final Gson gson = new Gson();
    private final String endPoint;
    private final String apiKey;
    private final String units;


    public OpenweathermapAPI(String endPoint, String units, String apiKey) {
        this.endPoint = endPoint;
        this.units = units;
        this.apiKey = apiKey;
    }

    public OpenweathermapResponse getWeatherForecast48(String city) throws OpenweathermapException, IOException, InterruptedException, URISyntaxException {
        URIBuilder b = new URIBuilder(endPoint);
        b.addParameter("q", city);
        b.addParameter("appid", apiKey);
        b.addParameter("units", units);
        URL url = b.build().toURL();
        String response = http.GET(url);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = df.format(new Date());
        OpenweathermapResponse openweathermapResponse = gson.fromJson(response, OpenweathermapResponse.class);
        filterNDays(openweathermapResponse, startDate, 2);
        return openweathermapResponse;
    }

    public void filterNDays(OpenweathermapResponse openweathermapResponse, String startDate, int numDays){
        List<String> allowedDate = getDatePlusNInterval(startDate, numDays);
        openweathermapResponse.list = openweathermapResponse.list
                .stream()
                .filter(element -> allowedDate.contains(element.dt_txt.split(" ")[0]))
                .collect(Collectors.toList());
    }

    public List<String> getDatePlusNInterval(String startDate, int n){
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i <= n; i++){
            dateList.add(LocalDate.parse(startDate)
                    .plusDays(i)
                    .toString()
            );
        }
        return dateList;
    }
}
