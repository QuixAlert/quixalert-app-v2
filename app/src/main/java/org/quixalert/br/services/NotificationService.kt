package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Notification
import org.quixalert.br.domain.repository.NotificationRepository
import javax.inject.Inject


class NotificationService @Inject constructor(
    private val notificationRepository: NotificationRepository
): IGenericService<Notification, String> {

    override fun add(entity: Notification) {
        notificationRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Notification) {
        notificationRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        notificationRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Notification>> {
        return notificationRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<Notification?> {
        return notificationRepository.getById(entityId)
    }
}