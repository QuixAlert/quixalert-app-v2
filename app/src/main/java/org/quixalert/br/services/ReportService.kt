package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.Report
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
}