package com.example.cpsc411final.data.auth

import com.example.cpsc411final.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository(private val auth: FirebaseAuth) {

    fun currentUser(): User? = auth.currentUser?.toUserModel()

    fun authStateFlow(): Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.toUserModel())
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    suspend fun signUp(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        runCatching {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            authResult.user!!.toUserModel()
        }
    }

    suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        runCatching {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult.user!!.toUserModel()
        }
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        auth.signOut()
    }

    private fun FirebaseUser.toUserModel(): User {
        return User(uid = uid, email = email)
    }
}