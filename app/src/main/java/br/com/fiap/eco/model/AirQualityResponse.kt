package br.com.fiap.eco.model

import com.google.gson.annotations.SerializedName

data class AirQualityResponse(
    val list: List<AirQualityData>
)

data class AirQualityData(
    val main: AirQualityMain,
    val components: AirQualityComponents
)

data class AirQualityMain(
    val aqi: Int // 1=Good, 2=Fair, 3=Moderate, 4=Poor, 5=Very Poor
)

data class AirQualityComponents(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    @SerializedName("pm2_5") val pm25: Double,
    val pm10: Double,
    val nh3: Double
)
