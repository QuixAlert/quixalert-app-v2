package org.quixalert.br.domain.repository

import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.AnimalExtraInfo
import org.quixalert.br.domain.model.AnimalGender
import org.quixalert.br.domain.model.AnimalSize
import org.quixalert.br.domain.model.AnimalType
import org.quixalert.br.domain.retrofit.RetrofitClient
import javax.inject.Inject

class AnimalRepository @Inject constructor() : FirebaseRepository<Animal, String>(
    collectionName = "animal",
    entityClass = Animal::class.java
) {
    private val catApi = RetrofitClient.catApiService
    private val dogApi = RetrofitClient.dogApiService

    override fun getAll(): Deferred<List<Animal>> = scope.async {
        try {
            val firebaseAnimals = super.getAll().await()

            val catImages = catApi.getCatImages()
            val dogImages = dogApi.getDogImages()

            val cats = catImages.map { catImage ->
                Animal(
                    id = catImage.id,
                    name = getRandomCatName(),
                    image = catImage.url,
                    gender = getRandomGender(),
                    type = AnimalType.CAT,
                    age = (1..15).random(),
                    species = "Felis catus",
                    description = "Um adorável gato à procura de um lar!",
                    size = AnimalSize.values().random(),
                    extraInfo = AnimalExtraInfo()
                )
            }

            val dogs = dogImages.map { dogImage ->
                Animal(
                    id = dogImage.id,
                    name = getRandomDogName(),
                    image = dogImage.url,
                    gender = getRandomGender(),
                    type = AnimalType.DOG,
                    age = (1..15).random(),
                    species = "Canis lupus familiaris",
                    description = "Um adorável cachorro à procura de um lar!",
                    size = AnimalSize.values().random(),
                    extraInfo = AnimalExtraInfo()
                )
            }

            return@async cats + dogs + firebaseAnimals

        } catch (e: Exception) {
            Log.e("${this@AnimalRepository::class.java}", "Error fetching images: ${e.message}")
            emptyList()
        }
    }

    private fun getRandomCatName(): String {
        val names = listOf(
            "Whiskers",
            "Mittens",
            "Shadow",
            "Luna",
            "Simba",
            "Nala",
            "Salem",
            "Coco",
            "Oliver",
            "Milo"
        )
        return names.random()
    }

    private fun getRandomDogName(): String {
        val names = listOf(
            "Buddy",
            "Bella",
            "Charlie",
            "Max",
            "Lucy",
            "Bailey",
            "Molly",
            "Cooper",
            "Daisy",
            "Rocky"
        )
        return names.random()
    }

    private fun getRandomGender(): AnimalGender {
        return AnimalGender.values().random()
    }
}