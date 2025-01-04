package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.repository.AdoptionRepository
import javax.inject.Inject

class AdoptionService @Inject constructor(
    private val adoptionRepository: AdoptionRepository
): IGenericService<Adoption, String> {

    override fun add(entity: Adoption) {
        adoptionRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Adoption) {
        adoptionRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        adoptionRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Adoption>> {
        return adoptionRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<Adoption?> {
        return adoptionRepository.getById(entityId)
    }
}