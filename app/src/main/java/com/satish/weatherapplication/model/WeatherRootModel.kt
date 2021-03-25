package com.satish.weatherapplication.model

class WeatherRootModel {
    private var base: String? = null
    private var main: MainModel? = null
    private var visibility = 0
    private var dt = 0
    private var timezone = 0
    private var id = 0
    private var name: String? = null
    private var cod = 0
    fun getBase(): String? {
        return base
    }

    public fun setBase(base: String?) {
        this.base = base
    }

    public fun getMain(): MainModel? {
        return main
    }

    public fun setMain(main: MainModel?) {
        this.main = main
    }

    fun getVisibility(): Int {
        return visibility
    }

    fun setVisibility(visibility: Int) {
        this.visibility = visibility
    }

    fun getDt(): Int {
        return dt
    }

    fun setDt(dt: Int) {
        this.dt = dt
    }

    fun getTimezone(): Int {
        return timezone
    }

    fun setTimezone(timezone: Int) {
        this.timezone = timezone
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCod(): Int {
        return cod
    }

    fun setCod(cod: Int) {
        this.cod = cod
    }
}