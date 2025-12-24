package com.example.cpsc411final.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cpsc411final.data.model.Course
import com.example.cpsc411final.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    val courses = repository.getCourses().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    fun saveCourse(course: Course) {
        viewModelScope.launch { repository.saveCourse(course) }
    }

    fun deleteCourse(courseId: String) {
        viewModelScope.launch { repository.deleteCourse(courseId) }
    }
}
