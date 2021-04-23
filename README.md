![N|Solid](https://www.ing.it/includes/v2020/img/logo-primary-large.svg)
# ING Bank Milan Branch - Java Case Study
## _Little weather forecast_

![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)

The senior candidate, with java/spring framework skills, should implement an application that given a **city**, provides the weather forecast for the next two days.

This application **must** use the OpenWeather APIs free (https://openweathermap.org/api) to retrieve the weather forecast and, given a city, **must** provide the following information for the next 48 hours:

- maximum, feels like temperatures, humidity.

The service **must** expose a REST API to make requests for weather forecasts.


## Must to have

1. The application implementation **must** use Spring Boot or Spring Web with Maven to
manage a project's build.
If you choose Spring Web, the artifact (.war) produced by the build **must** be potentially deployable on an application server (**JBoss** or **Tomcat** for example).
2. Code must be written in Java with a version greater than or equal to 8.
3. Documentation to build and run the service **must** be available (README.MD).
4. The application project **must** contain unit tests.

## Nice to have

- GUI documentation of the REST API (ex. SwaggerUI).

## Application sharing

For sharing the application, we’d like to get the project’s code from a free GIT repository (ex. GitHub) or equivalent.

___
## Instructions

- Download the project folder on your PC.
- Open the terminal, move under the directory `/backend/weather-forecast` and run the command `./mvnw spring-boot:run` (Now you have your backend server running on localhost:8080).
- Open the file `index.html` in the directory `/frontend` with your Browser (The test of the GUI has been done using Google Chrome).
- You can find the Rest API documentation at `http://localhost:8080/WeatherForecast/swagger-ui/index.html`  


## License

MIT


