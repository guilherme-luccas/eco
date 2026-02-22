package br.com.fiap.eco.model

import com.google.gson.annotations.SerializedName

data class OneCallResponse(
    val lat: Double,
    val lon: Double,
    val current: CurrentWeather
)

data class CurrentWeather(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val humidity: Int,
    val uvi: Double,
    val clouds: Int,
    val weather: List<WeatherCondition>
)

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
