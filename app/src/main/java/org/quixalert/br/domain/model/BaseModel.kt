package org.quixalert.br.domain.model

open class BaseModel(
    open var id: String = ""
) {
    private var documentId: String? = null

    fun setDocumentId(docId: String) {
        this.documentId = docId
    }
}