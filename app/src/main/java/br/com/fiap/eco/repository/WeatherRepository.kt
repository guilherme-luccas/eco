package br.com.fiap.eco.repository

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.fiap.eco.factory.RetrofitClient
import br.com.fiap.eco.model.AirQualityComponents
import br.com.fiap.eco.model.AirQualityData
import br.com.fiap.eco.model.AirQualityMain
import br.com.fiap.eco.model.AirQualityResponse
import br.com.fiap.eco.model.CurrentWeather
import br.com.fiap.eco.model.GeocodingResponse
import br.com.fiap.eco.model.OneCallResponse
import br.com.fiap.eco.model.OpenMeteoAirQualityResponse
import br.com.fiap.eco.model.OpenMeteoForecastResponse
import br.com.fiap.eco.model.OpenMeteoGeocodingWrapper
import br.com.fiap.eco.model.OpenMeteoMappers
import br.com.fiap.eco.model.WeatherCondition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun getCoordinates(city: String): GeocodingResponse? {
    var geo by remember {
        mutableStateOf<GeocodingResponse?>(null)
    }

    LaunchedEffect(city) {
        val call = RetrofitClient.getOpenMeteoService().getCoordinates(city = city, count = 1)
        call.enqueue(object : Callback<OpenMeteoGeocodingWrapper> {
            override fun onResponse(
                call: Call<OpenMeteoGeocodingWrapper>,
                response: Response<OpenMeteoGeocodingWrapper>
            ) {
                if (response.isSuccessful) {
                    val wrapper = response.body()
                    val first = wrapper?.results?.firstOrNull()
                    if (first != null) {
                        geo = GeocodingResponse(
                            name = first.name,
                            lat = first.latitude,
                            lon = first.longitude,
                            country = first.country ?: first.country_code ?: ""
                        )
                    }
                }
            }

            override fun onFailure(call: Call<OpenMeteoGeocodingWrapper>, t: Throwable) {
                println("Error fetching coordinates: ${t.message}")
            }
        })
    }

    return geo
}

@Composable
fun getAirQuality(lat: Double, lon: Double): AirQualityResponse? {
    var airQuality by remember {
        mutableStateOf<AirQualityResponse?>(null)
    }

    LaunchedEffect(lat, lon) {
        val call = RetrofitClient.getOpenMeteoService().getAirQuality(latitude = lat, longitude = lon)
        call.enqueue(object : Callback<OpenMeteoAirQualityResponse> {
        override fun onResponse(
            call: Call<OpenMeteoAirQualityResponse>,
            response: Response<OpenMeteoAirQualityResponse>
        ) {
            if (response.isSuccessful) {
                val body = response.body()
                val hourly = body?.hourly

                if (hourly != null && hourly.usAqi != null && hourly.usAqi.isNotEmpty()) {
                    // Pega o primeiro ponto horário disponível
                    val index = 0
                    val usAqi = hourly.usAqi.getOrNull(index)
                    val pm10 = hourly.pm10?.getOrNull(index) ?: 0.0
                    val pm25 = hourly.pm25?.getOrNull(index) ?: 0.0

                    val aqi = OpenMeteoMappers.convertUsAirQualityIndexToAppScale(usAqi)
                    airQuality = AirQualityResponse(
                        list = listOf(
                            AirQualityData(
                                main = AirQualityMain(aqi = aqi),
                                components = AirQualityComponents(
                                    co = 0.0,
                                    no = 0.0,
                                    no2 = 0.0,
                                    o3 = 0.0,
                                    so2 = 0.0,
                                    pm25 = pm25,
                                    pm10 = pm10,
                                    nh3 = 0.0
                                )
                            )
                        )
                    )
                } else {
                    // Se a API não retornar dados utilizáveis, evita ficar em "Carregando..."
                    airQuality = AirQualityResponse(
                        list = listOf(
                            AirQualityData(
                                main = AirQualityMain(aqi = 3), // Moderado (fallback)
                                components = AirQualityComponents(
                                    co = 0.0,
                                    no = 0.0,
                                    no2 = 0.0,
                                    o3 = 0.0,
                                    so2 = 0.0,
                                    pm25 = 0.0,
                                    pm10 = 0.0,
                                    nh3 = 0.0
                                )
                            )
                        )
                    )
                }
            } else {
                // Falha HTTP -> define valor padrão para não ficar eternamente carregando
                airQuality = AirQualityResponse(
                    list = listOf(
                        AirQualityData(
                            main = AirQualityMain(aqi = 3),
                            components = AirQualityComponents(
                                co = 0.0,
                                no = 0.0,
                                no2 = 0.0,
                                o3 = 0.0,
                                so2 = 0.0,
                                pm25 = 0.0,
                                pm10 = 0.0,
                                nh3 = 0.0
                            )
                        )
                    )
                )
            }
        }

        override fun onFailure(call: Call<OpenMeteoAirQualityResponse>, t: Throwable) {
            println("Error fetching air quality: ${t.message}")
            airQuality = AirQualityResponse(
                list = listOf(
                    AirQualityData(
                        main = AirQualityMain(aqi = 3),
                        components = AirQualityComponents(
                            co = 0.0,
                            no = 0.0,
                            no2 = 0.0,
                            o3 = 0.0,
                            so2 = 0.0,
                            pm25 = 0.0,
                            pm10 = 0.0,
                            nh3 = 0.0
                        )
                    )
                )
            )
        }
    })
    }

    return airQuality
}

@Composable
fun getCurrentWeather(lat: Double, lon: Double): OneCallResponse? {
    var weather by remember {
        mutableStateOf<OneCallResponse?>(null)
    }

    LaunchedEffect(lat, lon) {
        val call = RetrofitClient.getOpenMeteoService().getCurrentWeather(latitude = lat, longitude = lon)
        call.enqueue(object : Callback<OpenMeteoForecastResponse> {
            override fun onResponse(
                call: Call<OpenMeteoForecastResponse>,
                response: Response<OpenMeteoForecastResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val c = body.current
                        val uvi = body.daily?.uvIndexMax?.firstOrNull() ?: 0.0
                        val condition = OpenMeteoMappers.convertWmoWeatherCodeToWeatherCondition(c.weatherCode)
                        weather = OneCallResponse(
                            lat = body.latitude,
                            lon = body.longitude,
                            current = CurrentWeather(
                                temp = c.temperature2m,
                                feelsLike = c.apparentTemperature,
                                humidity = c.relativeHumidity2m,
                                uvi = uvi,
                                clouds = c.cloudCover,
                                weather = listOf(condition)
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<OpenMeteoForecastResponse>, t: Throwable) {
                println("Error fetching weather: ${t.message}")
            }
        })
    }

    return weather
}
