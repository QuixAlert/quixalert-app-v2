package org.quixalert.br.domain.repository
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.quixalert.br.domain.model.BaseModel
import java.util.UUID

abstract class FirebaseRepository<T : BaseModel, R>(
    private val collectionName: String,
    private val entityClass: Class<T>
) : IFirebaseRepository<T, R> {

    private val collection = Firebase.firestore.collection(collectionName)
    protected val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun add(entity: T) {
        scope.launch {
            try {
                val documentId = if (entity.id.isBlank()) UUID.randomUUID().toString() else entity.id
                entity.id = documentId
                collection.document(documentId).set(entity).await()
                withContext(Dispatchers.Main) {
                    Log.d("${this@FirebaseRepository::class.java}", "Successfully saved data.")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Log.e("${this@FirebaseRepository::class.java}", it) }
                }
            }
        }
    }


    override fun updateById(entityId: R, entity: T) {
        scope.launch {
            try {
                collection.document(entityId.toString()).set(entity).await()
                withContext(Dispatchers.Main) {
                    Log.d("${this@FirebaseRepository::class.java}", "Successfully updated data.")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Log.e("${this@FirebaseRepository::class.java}", it) }
                }
            }
        }
    }

    override fun deleteById(entityId: R) {
        scope.launch {
            try {
                collection.document(entityId.toString()).delete().await()
                withContext(Dispatchers.Main) {
                    Log.d("${this@FirebaseRepository::class.java}", "Successfully deleted data.")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Log.e("${this@FirebaseRepository::class.java}", it) }
                }
            }
        }
    }

    override fun getAll(): Deferred<List<T>> = scope.async {
        try {
            val snapshot = collection.get().await()
            snapshot.documents.mapNotNull { document ->
                document.toObject(entityClass)?.apply { setDocumentId(document.id) }
            }
        } catch (e: Exception) {
            Log.e("${this@FirebaseRepository::class.java}", "Error fetching documents: ${e.message}")
            emptyList()
        }
    }

    override fun getById(entityId: R): Deferred<T?> = scope.async {
        try {
            val snapshot = collection.document(entityId.toString()).get().await()
            snapshot.toObject(entityClass)?.apply { setDocumentId(snapshot.id) }
        } catch (e: Exception) {
            Log.e("${this@FirebaseRepository::class.java}", "Error fetching document: ${e.message}")
            null
        }
    }
}