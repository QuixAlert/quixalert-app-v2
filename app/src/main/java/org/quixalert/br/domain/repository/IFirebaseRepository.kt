package org.quixalert.br.domain.repository

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.BaseModel

interface IFirebaseRepository<T : BaseModel, R> {

    fun add(entity: T)

    fun updateById(entityId: R, entity: T)

    fun deleteById(entityId: R)

    fun getAll(): Deferred<List<T>>

    fun getById(entityId: R): Deferred<T?>
}
