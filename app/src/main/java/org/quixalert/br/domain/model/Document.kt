package org.quixalert.br.domain.model

data class Document(
    override var id: String = "",
    val documentType: DocumentType = DocumentType.ALVARA,
    val descriptions: String = "",
    val address: String = "",
    val reason: String = "",
    val extraDetails: String = "",
    val userId: String = "",
    val status: DocumentStatus = DocumentStatus.EM_ANDAMENTO
): BaseModel(id)