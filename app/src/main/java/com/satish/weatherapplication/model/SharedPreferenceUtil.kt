package com.satish.weatherapplication.model

import android.content.Context
import com.google.gson.Gson

object SharedPreferenceUtil {
    private val PREFS_TAG: String? = "SharedPrefs"
    private val PRODUCT_TAG: String? = "MyWeatherRootModel"
    private val PREFERENCE_TAG: String? = "SharedPrefs"

    fun setModelDataToSharedPreferences(key: String?, weatherRootModel: WeatherRootModel?, applicationContext: Context?) {
        val sharedPref = applicationContext!!.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(weatherRootModel)
        editor.putString(key, json)
        editor.commit()
    }

    fun getModelFromSharedPreferences(applicationContext: Context?, key: String?): WeatherRootModel? {
        val sharedPref = applicationContext!!.getSharedPreferences(key, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString(key, "")
        return gson.fromJson(json, WeatherRootModel::class.java)
    }

    fun setKeyPrefs(key: String?, value: String?, applicationContext: Context?) {
        val sharedPref = applicationContext!!.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getKeyPrefs(key: String?, applicationContext: Context?): String? {
        val sharedPref = applicationContext!!.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        return sharedPref.getString(key, "")
    }
}