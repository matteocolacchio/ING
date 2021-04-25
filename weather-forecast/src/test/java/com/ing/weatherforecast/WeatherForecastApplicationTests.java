package com.ing.weatherforecast;

import static org.assertj.core.api.Assertions.assertThat;
import com.ing.weatherforecast.controller.WeatherForecastController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class WeatherForecastApplicationTests {

	@Autowired
	private WeatherForecastController weatherForecastController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(weatherForecastController).isNotNull();
	}
}