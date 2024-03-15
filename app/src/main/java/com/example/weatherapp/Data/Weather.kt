import com.google.gson.annotations.SerializedName

data class Weather(
    val main: Main,
    val wind: Wind,
    val dt: Long,
    val sys: Sys,
    val weather: List<WeatherDescription>,
)

data class Main(
    val temp: Double,
    val humidity: Double
)

data class Wind(
    val speed: Double
)

data class Sys(
    val sunrise: Long,
    val sunset: Long
)

data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

//Data Class for Hourly Response
data class WeatherAPIResponse(
    val location: Location,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
)

data class Condition(
    val text: String,
    val code: Int,
    val icon: String
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val date_epoch: Long,
    val day: Day,
    val astro: Astro,
    val hour: List<Hour>
)

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_f: Double,
    val condition: Condition,
    // Add other desired daily forecast data fields
)

data class Astro(
    val sunrise: String,
    val sunset: String
)

data class Hour(
    val time_epoch: Long,
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val condition: Condition,
    val wind_kph: Double,
    val wind_mph: Double,
    val pressure_mb: Int,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val chance_of_rain: Int,
    val chance_of_snow: Int,
    val precip_mm: Double,
    val snow_cm: Double,
    val windchill_c: Double,
    val windchill_f: Double,
    val heatindex_c: Double,
    val heatindex_f: Double,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val will_it_rain: Int,
    val will_it_snow: Int,
    // Add other desired hourly forecast data fields
)
