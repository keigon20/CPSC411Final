package com.example.cpsc411final.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item(
    @DocumentId
    val documentId: String = "", // Renamed from 'id' to resolve conflict
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val createdAt: Long = 0L
)
