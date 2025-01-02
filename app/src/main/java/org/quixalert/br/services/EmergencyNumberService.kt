package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.EmergencyNumber
import org.quixalert.br.domain.repository.AdoptionRepository
import org.quixalert.br.domain.repository.EmergencyNumberRepository
import javax.inject.Inject

class EmergencyNumberService @Inject constructor(
    private val emergencyNumber: EmergencyNumberRepository
): IGenericService<EmergencyNumber, String> {

    override fun add(entity: EmergencyNumber) {
        emergencyNumber.add(entity)
    }

    override fun updateById(entityId: String, entity: EmergencyNumber) {
        emergencyNumber.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        emergencyNumber.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<EmergencyNumber>> {
        return emergencyNumber.getAll()
    }

    override fun getById(entityId: String): Deferred<EmergencyNumber?> {
        return emergencyNumber.getById(entityId)
    }
}