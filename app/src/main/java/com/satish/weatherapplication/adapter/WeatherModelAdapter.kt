package com.satish.weatherapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satish.weatherapplication.R
import com.satish.weatherapplication.adapter.WeatherModelAdapter.MyViewHolder
import com.satish.weatherapplication.model.WeatherRootModel

class WeatherModelAdapter(weatherRootModelList: MutableList<WeatherRootModel?>?) : RecyclerView.Adapter<MyViewHolder?>() {
    private val mWeatherRootModelList: MutableList<WeatherRootModel?>?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_weather_single_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherRootModel = mWeatherRootModelList!!.get(position)
        val stringBuilder = StringBuilder("")
        if (weatherRootModel != null) {
            val mainModel = weatherRootModel.getMain()
            stringBuilder.append("ID : " + weatherRootModel.getId())
            stringBuilder.append(""" City Name : ${weatherRootModel.getName()}""")
            stringBuilder.append(""" Temperature : ${mainModel!!.temp}""")
            stringBuilder.append(""" Min Temp : ${mainModel!!.temp_min}""")
            stringBuilder.append(""" Max Temp : ${mainModel!!.temp_max}""")
            stringBuilder.append(""" feels_like : ${mainModel!!.feels_like}""")
        }
        holder.mTextView!!.setText(stringBuilder.toString())
    }

    override fun getItemCount(): Int {
        return mWeatherRootModelList?.size ?: 0
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView?

        init {
            mTextView = itemView.findViewById(R.id.textView)
        }
    }

    init {
        notifyDataSetChanged()
        mWeatherRootModelList = weatherRootModelList
    }
}