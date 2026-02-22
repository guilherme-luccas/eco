package br.com.fiap.eco.model

/**
 * Maps Open-Meteo WMO weather_code to OpenWeather-style id, main, description
 * so existing UI (HomeScreen, TipsScreen) keeps working without changes.
 * WMO: https://open-meteo.com/en/docs#weather-variables
 */
object OpenMeteoMappers {

    fun convertWmoWeatherCodeToWeatherCondition(weatherCode: Int): WeatherCondition {
        val (id, main, description) = when (weatherCode) {
            0 -> Triple(800, "Clear", "Céu limpo")
            1 -> Triple(801, "Clouds", "Principalmente limpo")
            2 -> Triple(802, "Clouds", "Parcialmente nublado")
            3 -> Triple(803, "Clouds", "Nublado")
            45, 48 -> Triple(741, "Fog", "Neblina")
            51, 53, 55 -> Triple(301, "Drizzle", "Garoa")
            56, 57 -> Triple(611, "Rain", "Garoa congelante")
            61, 63, 65 -> Triple(500, "Rain", "Chuva")
            66, 67 -> Triple(616, "Rain", "Chuva congelante")
            71, 73, 75 -> Triple(600, "Snow", "Neve")
            77 -> Triple(621, "Snow", "Grãos de neve")
            80, 81, 82 -> Triple(520, "Rain", "Chuva passageira")
            85, 86 -> Triple(622, "Snow", "Neve passageira")
            95 -> Triple(211, "Thunderstorm", "Trovoada")
            96, 99 -> Triple(212, "Thunderstorm", "Trovoada com granizo")
            else -> Triple(800, "Clear", "Céu limpo")
        }
        return WeatherCondition(id = id, main = main, description = description, icon = "01d")
    }

    fun convertUsAirQualityIndexToAppScale(usAqi: Int?): Int {
        if (usAqi == null) return 1
        return when {
            usAqi <= 50 -> 1
            usAqi <= 100 -> 2
            usAqi <= 150 -> 3
            usAqi <= 200 -> 4
            else -> 5
        }
    }
}
