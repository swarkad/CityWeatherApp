package com.satish.weatherapplication

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satish.weatherapplication.adapter.WeatherModelAdapter
import com.satish.weatherapplication.model.SharedPreferenceUtil
import com.satish.weatherapplication.model.WeatherRootModel
import com.satish.weatherapplication.viewmodel.CityWeatherViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mWeatherRootModelList: MutableList<WeatherRootModel?>? = null
    private var cityWeatherViewModel: CityWeatherViewModel? = null
    private var searchCityAutoCompleteTextView: AutoCompleteTextView ? = null
    private var searchButton: Button? = null
    private var searchResultTextView: TextView? = null
    private var mRecyclerView: RecyclerView? = null
    private var mWeatherModelAdapter: WeatherModelAdapter? = null
    private var mSearchedCityArray = Array<String>(100) {""}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchCityAutoCompleteTextView = findViewById(R.id.search_city_AutoCompleteTextView)
        searchButton = findViewById(R.id.search_button)
        searchResultTextView = findViewById(R.id.search_result_details_textView)
        mRecyclerView = findViewById(R.id.all_lastSearchCityRecyclerView)

        mWeatherModelAdapter = WeatherModelAdapter(mWeatherRootModelList)
        mRecyclerView!!.setAdapter(mWeatherModelAdapter)
        mRecyclerView!!.adapter = mWeatherModelAdapter
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this))
        mWeatherRootModelList = ArrayList()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, mSearchedCityArray!!)
        searchCityAutoCompleteTextView!!.threshold = 1
        searchCityAutoCompleteTextView!!.setAdapter(adapter)

        cityWeatherViewModel = ViewModelProviders.of(this).get(CityWeatherViewModel::class.java)
        //cityWeatherViewModel.makeRestAPICall("Pune", this.getApplicationContext());
        cityWeatherViewModel!!.getCityWeatherViewModelObserver()!!.observe(this, { weatherRootModel ->
            if (weatherRootModel != null) {
                showDataOnTextView(weatherRootModel)
                SharedPreferenceUtil.setModelDataToSharedPreferences(weatherRootModel.getId().toString(),
                        weatherRootModel, applicationContext)
                var allKeys = SharedPreferenceUtil.getKeyPrefs(ALL_CITY_KEYS, applicationContext)
                allKeys = allKeys + "," + weatherRootModel.getId()
                SharedPreferenceUtil.setKeyPrefs(ALL_CITY_KEYS, allKeys, applicationContext)
                val strArray: Array<String?> = allKeys.split(",".toRegex()).toTypedArray()
                mWeatherRootModelList!!.clear()
                for (itemKey in strArray) {
                    mWeatherRootModelList!!.add(SharedPreferenceUtil.getModelFromSharedPreferences(applicationContext, itemKey))
                }
                mWeatherModelAdapter = WeatherModelAdapter(mWeatherRootModelList)
                mRecyclerView!!.setAdapter(mWeatherModelAdapter)
                showAutoCompleteData(mWeatherRootModelList)
            } else {
                searchResultTextView!!.setText("City is not find")
                Toast.makeText(this@MainActivity, "City is not find", Toast.LENGTH_LONG).show()
            }
        })

        searchButton!!.setOnClickListener(View.OnClickListener {
            val cityName = searchCityAutoCompleteTextView!!.getText().toString()
            if (TextUtils.isEmpty(cityName)) {
                Toast.makeText(this@MainActivity, "please enter city name", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            val position = isCityLocallyPresent(mWeatherRootModelList, cityName)
            if (position == -1) {
                cityWeatherViewModel!!.makeRestAPICall(cityName, this@MainActivity.applicationContext)
            } else {
                showDataOnTextView(mWeatherRootModelList!!.get(position))
            }
        })
    }


    fun showAutoCompleteData(weatherRootModelList: MutableList<WeatherRootModel?>?) {
        if (weatherRootModelList == null || weatherRootModelList.size == 0 ) {
            return;
        }

        for (index in weatherRootModelList.indices) {
            if (weatherRootModelList != null && weatherRootModelList[index] != null) {
                mSearchedCityArray.set(index, weatherRootModelList.get(index)!!.getName().toString())
            }
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, mSearchedCityArray!!)
        searchCityAutoCompleteTextView!!.threshold = 1
        searchCityAutoCompleteTextView!!.setAdapter(adapter)
    }

    fun isCityLocallyPresent(weatherRootModelList: MutableList<WeatherRootModel?>?, cityName: String?): Int {
        if (weatherRootModelList == null || weatherRootModelList.size == 0 || TextUtils.isEmpty(cityName)) return -1
        for (index in weatherRootModelList.indices) {
            if (weatherRootModelList != null && weatherRootModelList[index] != null &&
                    cityName.equals(weatherRootModelList[index]!!.getName(), ignoreCase = true)) {
                return index
            }
        }
        return -1
    }

    fun showDataOnTextView(weatherRootModel: WeatherRootModel?) {
        val stringBuilder = StringBuilder("")
        if (weatherRootModel != null) {
            val mainModel = weatherRootModel.getMain()
            stringBuilder.append(""" ID : ${weatherRootModel.getId()}""")
            stringBuilder.append(""" City Name : ${weatherRootModel.getName()}""")
            stringBuilder.append(""" Temperature : ${mainModel!!.temp}""")
            stringBuilder.append(""" Min Temp : ${mainModel.temp_min}""")
            stringBuilder.append(""" Max Temp : ${mainModel.temp_max}""")
            stringBuilder.append(""" feels_like : ${mainModel.feels_like}""")
        }
        searchResultTextView!!.setText(stringBuilder.toString())
    }

    companion object {
        val ALL_CITY_KEYS: String? = "ALL CITY KEYS"
    }
}