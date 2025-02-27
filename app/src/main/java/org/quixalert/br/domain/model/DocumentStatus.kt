package org.quixalert.br.domain.model

enum class DocumentStatus(val label: String, val color: Long) {
    EM_ANDAMENTO("Em andamento", 0xFF269996),  // Verde azulado (sua cor atual)
    CONCLUIDO("Concluído", 0xFF4CAF50),  // Verde
}