package br.com.fiap.eco.model

data class OpenMeteoGeocodingWrapper(
    val results: List<OpenMeteoGeocodingResult>?
)

data class OpenMeteoGeocodingResult(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?,
    val country_code: String?
)
