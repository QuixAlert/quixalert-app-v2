package org.quixalert.br.presentation.pages.reports

import org.quixalert.br.domain.model.Rating
import org.quixalert.br.domain.model.ReportStatus

data class ReportDetail(
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val description: String = "",
    val responsible: String = "",
    val responsibleIcon: String = "",
    val status: ReportStatus = ReportStatus.EM_ANALISE,
    val answer: String = "",
    val gallery: List<String> = emptyList(),
    val ratings: List<Rating> = emptyList()
) 