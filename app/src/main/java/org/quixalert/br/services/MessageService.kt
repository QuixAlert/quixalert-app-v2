package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Message
import org.quixalert.br.domain.repository.MessageRepository
import javax.inject.Inject

class MessageService @Inject constructor(
    private val messageRepository: MessageRepository
) : IGenericService<Message, String> {

    override fun add(entity: Message) {
        messageRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Message) {
        messageRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        messageRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Message>> = messageRepository.getAll()

    override fun getById(entityId: String): Deferred<Message?> = messageRepository.getById(entityId)

    fun getMessagesByAdoptionId(adoptionId: String): Deferred<List<Message>> =
        messageRepository.getMessagesByAdoptionId(adoptionId)
}