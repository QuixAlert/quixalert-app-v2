package org.quixalert.br.domain.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import org.quixalert.br.domain.model.Report
import javax.inject.Inject

class ReportRepository  @Inject constructor() : FirebaseRepository<Report, String>(
    collectionName = "report",
    entityClass = Report::class.java
){
    fun getReportByUserId(userId: String): Deferred<List<Report>> =
        CoroutineScope(Dispatchers.IO + SupervisorJob()).async {
            try {
                val snapshot = Firebase.firestore.collection("report")
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                snapshot.documents.mapNotNull { document ->
                    document.toObject(Report::class.java)?.apply { setDocumentId(document.id) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}