package org.quixalert.br.services

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

class FirebaseStorageService @Inject constructor() {

    fun uploadImageToFirebase(context: Context, imageUri: Uri, userId: String, callback: (String?) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference.child("users/$userId/profile.jpg")

        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var length: Int

            if (inputStream != null) {
                while (inputStream.read(buffer).also { length = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, length)
                }
            }

            val imageData = byteArrayOutputStream.toByteArray()
            val uploadTask = storageRef.putBytes(imageData)

            uploadTask.addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    callback(uri.toString()) // Retorna a URL pública da imagem
                }.addOnFailureListener {
                    callback(null) // Falha ao obter URL
                }
            }.addOnFailureListener {
                callback(null) // Falha no upload
            }
        } catch (e: Exception) {
            Log.e("FirebaseUpload", "Erro ao converter URI para arquivo: ${e.message}")
            callback(null)
        }
    }
}
