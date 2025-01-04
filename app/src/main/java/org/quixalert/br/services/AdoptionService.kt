package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.repository.AdoptionRepository
import javax.inject.Inject

class AdoptionService @Inject constructor(
    private val adoptionRepository: AdoptionRepository
): IGenericService<AdoptionT, String> {

    override fun add(entity: AdoptionT) {
        adoptionRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: AdoptionT) {
        adoptionRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        adoptionRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<AdoptionT>> {
        return adoptionRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<AdoptionT?> {
        return adoptionRepository.getById(entityId)
    }
}