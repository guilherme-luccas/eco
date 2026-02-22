package br.com.fiap.eco.model

import com.google.gson.annotations.SerializedName

data class OpenMeteoForecastResponse(
    val latitude: Double,
    val longitude: Double,
    val current: OpenMeteoCurrent,
    val daily: OpenMeteoDaily?
)

data class OpenMeteoCurrent(
    val time: String,
    @SerializedName("temperature_2m") val temperature2m: Double,
    @SerializedName("relative_humidity_2m") val relativeHumidity2m: Int,
    @SerializedName("apparent_temperature") val apparentTemperature: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("cloud_cover") val cloudCover: Int
)

data class OpenMeteoDaily(
    @SerializedName("uv_index_max") val uvIndexMax: List<Double>?
)
