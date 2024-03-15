package com.example.weatherapp.Data


import Weather
import WeatherAPIResponse
import android.util.Log
import androidx.constraintlayout.motion.widget.Debug.getLocation

class WeatherRepository {
    private val apiService: WeatherApiService = RetrofitClient.openWeatherApiService
    private val hourlyApiService: WeatherApiService = anotherRetrofitClient.hourlyweatherapiService

    suspend fun getWeather(latitude: Double, longtiude: Double, apiKey: String): Weather {
        Log.i("tryit", "this has been reached")
        Log.i("tryit", apiService.getWeatherByLocation("Addis Ababa", apiKey).toString())
        return apiService.getWeatherByCordinate(latitude, longtiude, apiKey)
    }


    suspend fun getHourlyForecast(city: String, apiKey: String): WeatherAPIResponse {

        Log.i("tryyit", "${hourlyApiService.getForecast(apiKey, "Addis Ababa")}")

        return hourlyApiService.getForcastByCordinate(apiKey, city)
    }
}
