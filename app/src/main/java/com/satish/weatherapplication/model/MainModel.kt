package com.satish.weatherapplication.model

class MainModel(mainModel: MainModel) {
    public var id = 0
    public var temp: Double
    public var feels_like: Double
    public var temp_min: Double
    public var temp_max: Double
    public var pressure: Int
    public var humidity: Int
    public var sea_level: Int
    public var grnd_level: Int

    init {
        temp = mainModel.temp
        feels_like = mainModel.feels_like
        temp_min = mainModel.temp_min
        temp_max = mainModel.temp_max
        pressure = mainModel.pressure
        humidity = mainModel.humidity
        sea_level = mainModel.sea_level
        grnd_level = mainModel.grnd_level
    }
}