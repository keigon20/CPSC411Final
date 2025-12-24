package com.example.cpsc411final.data.model

import com.google.firebase.firestore.DocumentId

data class Course(
    @DocumentId
    val documentId: String = "",
    val name: String = "",
    val location: String = "",
    val par: Int = 72,
    val holes: Int = 18
)
