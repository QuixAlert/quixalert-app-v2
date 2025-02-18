package org.quixalert.br.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import org.quixalert.br.domain.model.Report
import org.quixalert.br.domain.model.Rating
import org.quixalert.br.domain.model.ReportStatus
import javax.inject.Inject

class ReportRepository @Inject constructor() : FirebaseRepository<Report, String>(
    collectionName = "report",
    entityClass = Report::class.java
) {
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

    fun submitRating(reportId: String, rating: Int, comment: String): Deferred<Unit> =
        CoroutineScope(Dispatchers.IO + SupervisorJob()).async {
            try {
                val currentUser = Firebase.auth.currentUser
                val newRating = Rating(
                    rating = rating,
                    comment = comment,
                    timestamp = System.currentTimeMillis().toString(),
                    userId = currentUser?.uid ?: ""
                )

                // Buscar o relatório atual
                val reportDoc = Firebase.firestore.collection("report")
                    .document(reportId)
                    .get()
                    .await()

                val currentReport = reportDoc.toObject(Report::class.java)
                val currentRatings = currentReport?.ratings ?: listOf()
                val updatedRatings = ArrayList<Rating>(currentRatings).apply {
                    add(newRating)
                }

                // Atualizar o documento com a nova lista de avaliações
                Firebase.firestore.collection("report")
                    .document(reportId)
                    .update("ratings", updatedRatings)
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }

    fun updateStatus(reportId: String, newStatus: ReportStatus): Deferred<Unit> =
        CoroutineScope(Dispatchers.IO + SupervisorJob()).async {
            try {
                Firebase.firestore.collection("report")
                    .document(reportId)
                    .update("status", newStatus)
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
}