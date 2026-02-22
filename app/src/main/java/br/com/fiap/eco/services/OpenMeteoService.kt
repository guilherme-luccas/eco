package br.com.fiap.eco.services

import br.com.fiap.eco.model.OpenMeteoAirQualityResponse
import br.com.fiap.eco.model.OpenMeteoForecastResponse
import br.com.fiap.eco.model.OpenMeteoGeocodingWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {

    @GET("v1/forecast")
    fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,cloud_cover",
        @Query("daily") daily: String = "uv_index_max",
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("timezone") timezone: String = "auto"
    ): Call<OpenMeteoForecastResponse>

    @GET("https://geocoding-api.open-meteo.com/v1/search")
    fun getCoordinates(
        @Query("name") city: String,
        @Query("count") count: Int = 1,
        @Query("language") language: String = "pt"
    ): Call<OpenMeteoGeocodingWrapper>

    // API de qualidade do ar fica em outro host (não em api.open-meteo.com)
    @GET("https://air-quality-api.open-meteo.com/v1/air-quality")
    fun getAirQuality(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "us_aqi,pm10,pm2_5"
    ): Call<OpenMeteoAirQualityResponse>
}
