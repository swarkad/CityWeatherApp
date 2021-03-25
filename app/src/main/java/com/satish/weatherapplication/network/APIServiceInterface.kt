package com.satish.weatherapplication.network

import com.satish.weatherapplication.model.WeatherRootModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServiceInterface {
    // remaining url : weather?q=Pune&appid=094aa776d64c50d5b9e9043edd4ffd00
    @GET("data/2.5/weather")
    open fun getWeatherInfo(@Query("q") cityName: String?,
                            @Query("appid") appId: String?): Call<WeatherRootModel?>?

    companion object {
        val APP_ID: String? = "094aa776d64c50d5b9e9043edd4ffd00"
    }
}