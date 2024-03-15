package com.example.weatherapp.Data


import Weather
import WeatherAPIResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface WeatherApiService {
    @GET("weather")
    suspend fun getWeatherByCordinate(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Weather

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    )

    @GET("forecast.json") // Replace with the actual endpoint if different
    suspend fun getForecast(
        @Query("key") apiKey: String, // Replace with your API key
        @Query("q") location: String, // City name or coordinates
        @Query("days") days: Int = 3 // Number of forecast days (optional)
    ): WeatherAPIResponse

    @GET("forecast.json")
    suspend fun getForcastByCordinate(
        @Query("key") apikey:String,
        @Query("q") cordinate: String,
        @Query("days") days: Int = 3
    ): WeatherAPIResponse

}


object RetrofitClient {
    private const val BASE_URL_OPEN_WEATHER = "https://api.openweathermap.org/data/2.5/"

    val retrofitOpenWeather: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_OPEN_WEATHER)
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    val openWeatherApiService: WeatherApiService = retrofitOpenWeather.create(WeatherApiService::class.java)
}

object anotherRetrofitClient{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val hourlyweatherapiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}
