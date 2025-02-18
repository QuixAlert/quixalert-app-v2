package org.quixalert.br.domain.model

enum class ReportStatus(val label: String, val color: Long) {
    EM_ANALISE("Em análise", 0xFFFFA500),  // Laranja
    EM_ANDAMENTO("Em andamento", 0xFF269996),  // Verde azulado (sua cor atual)
    CONCLUIDO("Concluído", 0xFF4CAF50),  // Verde
    REJEITADO("Rejeitado", 0xFFE53935)  // Vermelho
} 