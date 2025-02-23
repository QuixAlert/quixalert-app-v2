package org.quixalert.br.domain.retrofit

import org.quixalert.br.domain.retrofit.services.CatService
import org.quixalert.br.domain.retrofit.services.DogService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val CAT_BASE_URL = "https://api.thecatapi.com/v1/"
    private const val DOG_BASE_URL = "https://api.thedogapi.com/v1/"

    val catApiService: CatService by lazy {
        Retrofit.Builder()
            .baseUrl(CAT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatService::class.java)
    }

    val dogApiService: DogService by lazy {
        Retrofit.Builder()
            .baseUrl(DOG_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogService::class.java)
    }
}