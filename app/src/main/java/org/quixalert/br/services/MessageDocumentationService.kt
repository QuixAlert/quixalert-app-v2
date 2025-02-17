package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.MessageDocumentation
import org.quixalert.br.domain.repository.MessageDocumentationRepository
import javax.inject.Inject

class MessageDocumentationService @Inject constructor(
    private val messageRepository: MessageDocumentationRepository
) : IGenericService<MessageDocumentation, String> {

    override fun add(entity: MessageDocumentation) {
        messageRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: MessageDocumentation) {
        messageRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        messageRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<MessageDocumentation>> = messageRepository.getAll()

    override fun getById(entityId: String): Deferred<MessageDocumentation?> = messageRepository.getById(entityId)

    fun getMessagesByDocumentId(documentId: String): Deferred<List<MessageDocumentation>> =
        messageRepository.getMessagesByAdoptionId(documentId)
}