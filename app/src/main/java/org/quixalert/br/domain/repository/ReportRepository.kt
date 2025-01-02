package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.Report
import javax.inject.Inject

class ReportRepository  @Inject constructor() : FirebaseRepository<Report, String>(
    collectionName = "report",
    entityClass = Report::class.java
)