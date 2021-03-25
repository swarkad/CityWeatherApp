package com.satish.weatherapplication.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satish.weatherapplication.model.WeatherRootModel
import com.satish.weatherapplication.network.APIServiceInterface
import com.satish.weatherapplication.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityWeatherViewModel : ViewModel() {
    private val mutableLiveData: MutableLiveData<WeatherRootModel?>?
    fun getCityWeatherViewModelObserver(): MutableLiveData<WeatherRootModel?>? {
        return mutableLiveData
    }

    fun makeRestAPICall(cityName: String?, applicationContext: Context?) {
        val apiServiceInterface = RetrofitInstance.getRetrofitClient(applicationContext)!!.create(APIServiceInterface::class.java)
        val call = apiServiceInterface.getWeatherInfo(cityName, APIServiceInterface.Companion.APP_ID)
        call!!.enqueue(object : Callback<WeatherRootModel?> {
            override fun onResponse(call: Call<WeatherRootModel?>?, response: Response<WeatherRootModel?>?) {
                mutableLiveData!!.postValue(response!!.body())
            }

            override fun onFailure(call: Call<WeatherRootModel?>?, t: Throwable?) {
                mutableLiveData!!.postValue(null)
            }
        })
    }

    init {
        mutableLiveData = MutableLiveData()
    }
}