package org.quixalert.br.domain.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import org.quixalert.br.domain.model.MessageDocumentation
import javax.inject.Inject

class MessageDocumentationRepository @Inject constructor() : FirebaseRepository<MessageDocumentation, String>(
    collectionName = "messagesDocumentation",
    entityClass = MessageDocumentation::class.java
) {
    fun getMessagesByAdoptionId(documentId: String): Deferred<List<MessageDocumentation>> =
        CoroutineScope(Dispatchers.IO + SupervisorJob()).async {
            try {
                val snapshot = Firebase.firestore.collection("messagesDocumentation")
                    .whereEqualTo("documentId", documentId)
                    .get()
                    .await()
                snapshot.documents.mapNotNull { document ->
                    document.toObject(MessageDocumentation::class.java)?.apply { setDocumentId(document.id) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}