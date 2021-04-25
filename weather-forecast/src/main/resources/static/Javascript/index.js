// Get the input field
var input = document.getElementById("cityInput");

// Execute a function when the user releases a key on the keyboard
input.addEventListener("keyup", function(event) {
    // Number 13 is the "Enter" key on the keyboard
    if (event.keyCode === 13) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        document.getElementById("searchButton").click();
    }
});


$("#searchButton").click(async function(){  
    $(".weatherForecast").empty();   
    $(".weatherForecast").hide();   
    const city = $("#cityInput").val();
    try {
        const WeatherForecast = await getWeatherForecast(city);
        drawWeatherForecast(WeatherForecast.message);
    } catch (error) {
        $(".weatherForecast").append('<div class="errorMessage">' + error + '</div>');
        $(".weatherForecast").show();
        console.error(error);
    }
});


$("#cityInput").click(function(){  
    $(".weatherForecast").empty();   
    $(".weatherForecast").hide();
});


async function getWeatherForecast(city) {
    try {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const url = "/WeatherForecast/?city=" + city;
        const requestOption = {
            method: 'GET',
            headers: myHeaders
        };
        const response = await fetch(url, requestOption);
        const jsonResponse = JSON.parse(await response.text());
        if (!response.ok) {
            throw ("Error: " + jsonResponse.error);
        }
        return jsonResponse;
    } catch (error) {
        throw (error);
    }
}


function getDayName(date){
    var d = new Date(date);
    var weekday = new Array(7);
    weekday[0] = "Sunday";
    weekday[1] = "Monday";
    weekday[2] = "Tuesday";
    weekday[3] = "Wednesday";
    weekday[4] = "Thursday";
    weekday[5] = "Friday";
    weekday[6] = "Saturday";
    var n = weekday[d.getDay()];
    return n;
}


function chunkArray(arr, size) {
    var groupedArray = [];
    for(var i = 0; i < arr.length; i += size) {
      groupedArray.push(arr.slice(i, i+size));
    }
    return groupedArray ;
}


function drawWeatherForecast(json){
    $("#errorMessage").empty();   
    $("#errorMessage").hide(); 
    $(".weatherForecast").hide();
    var weatherForecastDetails = "";
    weatherForecastDetails += `<div class="cityLabel">${json.city.name}</div>
                                    <div class="container-fluid">
                                        <div class="row">`;
    json.list.forEach(forecast => {
        var split = forecast.dt_txt.split(" ");
        var date = split[0];
        var time = split[1]
        weatherForecastDetails += `<div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
                                        <div class="container-fluid tableBox">
                                            <div class="table-responsive">
                                                <table class="table table-bordered table-dark table-custom">
                                                    <thead>
                                                        <th class="dayCell">${getDayName(date)} ${date.split("-")[2]} at ${time.substring(0, time.length - 3)}</th>
                                                        <th class="iconCell"><img src="./Image/${iconCodes[forecast.weather[0].id]}.png" alt="icon-${forecast.weather.id}"></th>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <th>Max Temp</th>
                                                            <td>${forecast.main.temp_max} &#8451;</td>
                                                        </tr>
                                                        <tr>
                                                            <th>Min Temp</th>
                                                            <td>${forecast.main.temp_min} &#8451;</td>
                                                        </tr>
                                                        <tr>
                                                            <th>Feels Temp</th>
                                                            <td>${forecast.main.feels_like} &#8451;</td>
                                                        </tr>
                                                        <tr>
                                                            <th>Humidity</th>
                                                            <td>${forecast.main.humidity}%</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div> 
                                    </div>`;
    });
    weatherForecastDetails += `</div>`;
    weatherForecastDetails += `</div>`;
    weatherForecastDetails += `</div>`;
    $(".weatherForecast").append(weatherForecastDetails);
    $(".weatherForecast").show();
}
