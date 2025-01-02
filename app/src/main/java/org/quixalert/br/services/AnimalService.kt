package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.repository.AnimalRepository
import javax.inject.Inject

class AnimalService @Inject constructor(
    private val animalRepository: AnimalRepository
): IGenericService<Animal, String> {

    override fun add(entity: Animal) {
        animalRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Animal) {
        animalRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        animalRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Animal>> {
        return animalRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<Animal?> {
        return animalRepository.getById(entityId)
    }
}