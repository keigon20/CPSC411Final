package com.example.cpsc411final.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item(
    @DocumentId
    val documentId: String = "",
    val userId: String = "",
    val course: String = "", // Renamed from title
    val score: String = "", // Renamed from description
    val createdAt: Long = 0L
)
