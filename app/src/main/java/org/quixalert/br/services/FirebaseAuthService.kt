package org.quixalert.br.services

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        profileImage: String,
        phone: String
    ): FirebaseUser? {
        return try {
            // Cria o usuário com o e-mail e senha
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            // Se o usuário foi criado com sucesso, atualize o nome e a foto de perfil
            val user = result.user
            user?.let {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)  // Definindo o nome
                    .setPhotoUri(profileImage.toUri())  // Definindo a foto de perfil
                    .build()

                it.updateProfile(profileUpdates).await()  // Atualiza as informações do perfil

                // Salva as informações adicionais (telefone) no Firestore
                val userData = hashMapOf(
                    "uid" to it.uid,
                    "name" to name,
                    "profileImage" to profileImage,
                    "phone" to phone,
                    "email" to email
                )

                // Salva no Firestore na coleção "users"
                val db = FirebaseFirestore.getInstance()
                db.collection("users").document(it.uid)
                    .set(userData)
                    .await()

                return it  // Retorna o usuário
            }

            null  // Retorna null caso o usuário não tenha sido criado

        } catch (e: Exception) {
            e.printStackTrace()
            null  // Em caso de erro, retorna null
        }
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun logoutUser() {
        firebaseAuth.signOut()
    }
}
