package br.com.fiap.eco.factory

import br.com.fiap.eco.services.OpenMeteoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val BASE_URL = "https://api.open-meteo.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getOpenMeteoService(): OpenMeteoService {
        return retrofit.create(OpenMeteoService::class.java)
    }
}
