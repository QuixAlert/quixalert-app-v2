package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Document
import org.quixalert.br.domain.repository.DocumentRepository
import javax.inject.Inject

class DocumentService @Inject constructor(
    private val documentRepository: DocumentRepository
): IGenericService<Document, String> {

    override fun add(entity: Document) {
        documentRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Document) {
        documentRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        documentRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Document>> {
        return documentRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<Document?> {
        return documentRepository.getById(entityId)
    }
    fun getDocumentByUserId(userId: String): Deferred<List<Document>> {
        return documentRepository.getDocumentByUserId(userId)
    }
}