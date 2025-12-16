package com.example.cpsc411final.data.model

/**
 * A data class representing a user in the application.
 *
 * @property uid The unique identifier for the user, typically from Firebase Auth.
 * @property email The user's email address.
 */
data class User(
    val uid: String = "",
    val email: String? = null
)