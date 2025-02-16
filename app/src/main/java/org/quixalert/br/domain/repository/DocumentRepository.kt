package org.quixalert.br.domain.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import org.quixalert.br.domain.model.Document
import javax.inject.Inject

class DocumentRepository  @Inject constructor() : FirebaseRepository<Document, String>(
    collectionName = "document",
    entityClass = Document::class.java
){
    fun getDocumentByUserId(userId: String): Deferred<List<Document>> =
        CoroutineScope(Dispatchers.IO + SupervisorJob()).async {
            try {
                val snapshot = Firebase.firestore.collection("document")
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                snapshot.documents.mapNotNull { document ->
                    document.toObject(Document::class.java)?.apply { setDocumentId(document.id) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}