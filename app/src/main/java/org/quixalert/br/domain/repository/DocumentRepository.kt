package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Document
import javax.inject.Inject

class DocumentRepository  @Inject constructor() : FirebaseRepository<Document, String>(
    collectionName = "document",
    entityClass = Document::class.java
)