package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Report
import org.quixalert.br.domain.model.ReportStatus
import org.quixalert.br.domain.repository.ReportRepository
import javax.inject.Inject

class ReportService @Inject constructor(
    private val reportRepository: ReportRepository
): IGenericService<Report, String> {

    override fun add(entity: Report) {
        reportRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Report) {
        reportRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        reportRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Report>> {
        return reportRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<Report?> {
        return reportRepository.getById(entityId)
    }
    fun getReportByUserId(userId: String): Deferred<List<Report>> {
        return reportRepository.getReportByUserId(userId)
    }

    fun submitRating(reportId: String, rating: Int, comment: String): Deferred<Unit> {
        return reportRepository.submitRating(reportId, rating, comment)
    }

    fun updateStatus(reportId: String, newStatus: ReportStatus): Deferred<Unit> {
        return reportRepository.updateStatus(reportId, newStatus)
    }
}