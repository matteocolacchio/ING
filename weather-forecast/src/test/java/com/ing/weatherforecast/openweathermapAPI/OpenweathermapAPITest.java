package com.ing.weatherforecast.openweathermapAPI;

import com.google.gson.Gson;
import com.ing.weatherforecast.model.openweathermapResponse.OpenweathermapResponse;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;


class OpenweathermapAPITest {
    private OpenweathermapAPI openweathermapAPI = new OpenweathermapAPI("endPoint", "metric", "apiKey");
    private Gson gson = new Gson();


    @Test
    void getDatePlusNIntervalTest() {
        List<String> dateRange = openweathermapAPI.getDatePlusNInterval("1990-09-27", 2);
        assertThat(dateRange).containsExactly("1990-09-27", "1990-09-28", "1990-09-29");
    }

    @Test
    void filterNDaysTest(){
        String json = "{\"cod\":\"200\",\"message\":0,\"cnt\":40,\"list\":[{\"dt\":1619038800,\"main\":{\"temp\":283.76,\"feels_like\":282.94,\"temp_min\":281.31,\"temp_max\":283.76,\"pressure\":1019,\"sea_level\":1019,\"grnd_level\":1000,\"humidity\":79,\"temp_kf\":2.45},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":67},\"wind\":{\"speed\":2.99,\"deg\":176,\"gust\":6.71},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"1990-09-27 21:00:00\"},{\"dt\":1619038800,\"main\":{\"temp\":283.76,\"feels_like\":282.94,\"temp_min\":281.31,\"temp_max\":283.76,\"pressure\":1019,\"sea_level\":1019,\"grnd_level\":1000,\"humidity\":79,\"temp_kf\":2.45},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":67},\"wind\":{\"speed\":2.99,\"deg\":176,\"gust\":6.71},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"1990-09-28 21:00:00\"},{\"dt\":1619038800,\"main\":{\"temp\":283.76,\"feels_like\":282.94,\"temp_min\":281.31,\"temp_max\":283.76,\"pressure\":1019,\"sea_level\":1019,\"grnd_level\":1000,\"humidity\":79,\"temp_kf\":2.45},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":67},\"wind\":{\"speed\":2.99,\"deg\":176,\"gust\":6.71},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"1990-09-29 21:00:00\"},{\"dt\":1619038800,\"main\":{\"temp\":283.76,\"feels_like\":282.94,\"temp_min\":281.31,\"temp_max\":283.76,\"pressure\":1019,\"sea_level\":1019,\"grnd_level\":1000,\"humidity\":79,\"temp_kf\":2.45},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":67},\"wind\":{\"speed\":2.99,\"deg\":176,\"gust\":6.71},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"1990-09-30 21:00:00\"}],\"city\":{\"id\":6542269,\"name\":\"Torremaggiore\",\"country\":\"IT\",\"coord\":{\"lon\":15.2963,\"lat\":41.6907}}}";
        OpenweathermapResponse openweathermapResponse = gson.fromJson(json, OpenweathermapResponse.class);
        openweathermapAPI.filterNDays(openweathermapResponse, "1990-09-27", 2);
        String allowedDateArray[] = {"1990-09-27", "1990-09-28", "1990-09-29"};
        List<String> allowedDateList = Arrays.asList(allowedDateArray);
        Boolean test = true;
        for (int i = 0; i < openweathermapResponse.list.size(); i++) {
            if (!allowedDateList.contains(openweathermapResponse.list.get(i).dt_txt.split(" ")[0])){
                test = false;
                break;
            }
        }
        assertTrue("The date interval filtering has been succeed!", test);
    }
}