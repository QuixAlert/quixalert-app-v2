package org.quixalert.br.domain.retrofit.services

import org.quixalert.br.domain.model.AnimalImage
import retrofit2.http.GET

interface CatService {
    @GET("images/search?limit=3")
    suspend fun getCatImages(): List<AnimalImage>
}