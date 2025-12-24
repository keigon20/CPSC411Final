package com.example.cpsc411final.data.repository

import com.example.cpsc411final.data.model.Course
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val coursesCollection = firestore.collection("courses")

    // READ: Get all courses as a Flow
    fun getCourses(): Flow<List<Course>> = callbackFlow {
        val subscription = coursesCollection.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val courses = snapshot.toObjects(Course::class.java)
                trySend(courses)
            }
        }
        awaitClose { subscription.remove() }
    }

    // CREATE / UPDATE
    suspend fun saveCourse(course: Course) {
        if (course.documentId.isEmpty()) {
            coursesCollection.add(course).await()
        } else {
            coursesCollection.document(course.documentId).set(course).await()
        }
    }

    // DELETE
    suspend fun deleteCourse(courseId: String) {
        coursesCollection.document(courseId).delete().await()
    }
}
