package org.quixalert.br.domain.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import org.quixalert.br.domain.model.AdoptionT
import javax.inject.Inject

class AdoptionRepository  @Inject constructor() : FirebaseRepository<AdoptionT, String>(
    collectionName = "adoption",
    entityClass = AdoptionT::class.java
){
    fun getAdoptionsByUserId(userId: String): Deferred<List<AdoptionT>> =
        CoroutineScope(Dispatchers.IO + SupervisorJob()).async {
            try {
                val snapshot = Firebase.firestore.collection("adoption")
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                snapshot.documents.mapNotNull { document ->
                    document.toObject(AdoptionT::class.java)?.apply { setDocumentId(document.id) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}