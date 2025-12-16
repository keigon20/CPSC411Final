package com.example.cpsc411final.data.remote

import com.example.cpsc411final.data.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(private val db: FirebaseFirestore) {

    fun itemStreamForUser(userId: String): Flow<List<Item>> = callbackFlow {
        val subscription = db.collection("items")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    trySend(snapshot.toObjects())
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun createItem(item: Item) {
        db.collection("items").add(item).await()
    }

    suspend fun updateItem(itemId: String, data: Map<String, Any>) {
        db.collection("items").document(itemId).update(data).await()
    }

    suspend fun deleteItem(itemId: String) {
        db.collection("items").document(itemId).delete().await()
    }
}