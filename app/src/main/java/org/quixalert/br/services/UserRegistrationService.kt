package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.UserRegistrationData
import org.quixalert.br.domain.repository.UserRegistrationDataRepository
import javax.inject.Inject

class UserRegistrationService @Inject constructor(
    private val userRegistrationDataRepository: UserRegistrationDataRepository
): IGenericService<UserRegistrationData, String> {

    override fun add(entity: UserRegistrationData) {
        userRegistrationDataRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: UserRegistrationData) {
        userRegistrationDataRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        userRegistrationDataRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<UserRegistrationData>> {
        return userRegistrationDataRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<UserRegistrationData?> {
        return userRegistrationDataRepository.getById(entityId)
    }
}