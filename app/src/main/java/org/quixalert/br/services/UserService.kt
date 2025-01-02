package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.User
import org.quixalert.br.domain.repository.UserRepository
import javax.inject.Inject

class UserService @Inject constructor(
    private val userRepository: UserRepository
): IGenericService<User, String> {

    override fun add(entity: User) {
        userRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: User) {
        userRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        userRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<User>> {
        return userRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<User?> {
        return userRepository.getById(entityId)
    }
}