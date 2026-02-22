package br.com.fiap.eco.model

import com.google.gson.annotations.SerializedName

data class OpenMeteoAirQualityResponse(
    val hourly: OpenMeteoAirQualityHourly?
)

data class OpenMeteoAirQualityHourly(
    val time: List<String>?,
    @SerializedName("us_aqi") val usAqi: List<Int>?,
    val pm10: List<Double>?,
    @SerializedName("pm2_5") val pm25: List<Double>?
)
