package org.quixalert.br.domain.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import org.quixalert.br.domain.model.Message
import javax.inject.Inject

class MessageRepository @Inject constructor() : FirebaseRepository<Message, String>(
    collectionName = "messages",
    entityClass = Message::class.java
) {
    fun getMessagesByAdoptionId(adoptionId: String): Deferred<List<Message>> =
        CoroutineScope(Dispatchers.IO + SupervisorJob()).async {
            try {
                val snapshot = Firebase.firestore.collection("messages")
                    .whereEqualTo("adoptionId", adoptionId)
                    .get()
                    .await()
                snapshot.documents.mapNotNull { document ->
                    document.toObject(Message::class.java)?.apply { setDocumentId(document.id) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}