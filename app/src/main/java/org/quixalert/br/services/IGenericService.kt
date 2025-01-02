package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.BaseModel

interface IGenericService<T : BaseModel, ID> {
    fun add(entity: T)

    fun updateById(entityId: ID, entity: T)

    fun deleteById(entityId: ID)

    fun getAll(): Deferred<List<T>>

    fun getById(entityId: ID): Deferred<T?>
}