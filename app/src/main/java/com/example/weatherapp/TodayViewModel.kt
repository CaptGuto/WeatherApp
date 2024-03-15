package com.example.weatherapp

import Weather
import WeatherAPIResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodayViewModel : ViewModel() {

    private val apiKey = "32943c93ee1825682ffadcc3962a2a1a"
    private val repository = WeatherRepository()

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    //Hourly Data
    private val _hourlyForecast = MutableLiveData<WeatherAPIResponse>()
    val hourlyForecast: LiveData<WeatherAPIResponse> = _hourlyForecast

    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _weather.postValue(repository.getWeather(latitude, longitude, apiKey))
            } catch (e: Exception) {
                Log.i("tryyit","${e.message}")
                // TODO: Handle the errors Here !!!!!!!!!!!!!!!!!!!! Don't forget
            }
        }
    }

    fun fetchHourlyForecast(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("tryyit", "this point has been reached")
                _hourlyForecast.postValue(repository.getHourlyForecast(city, " 6b57e7fad84a40bbb2f75614241003"))
            } catch (e: Exception) {
                Log.i("tryyit", "${e.message}")
            }
        }
    }
}
