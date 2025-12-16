package com.example.cpsc411final.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cpsc411final.data.auth.AuthRepository
import com.example.cpsc411final.data.model.Item
import com.example.cpsc411final.data.remote.FirestoreDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val firestore: FirestoreDataSource,
    private val authRepo: AuthRepository
) : ViewModel() {

    val items: StateFlow<List<Item>> = authRepo.authStateFlow()
        .flatMapLatest { user ->
            if (user != null) {
                firestore.itemStreamForUser(user.uid)
            } else {
                MutableStateFlow(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedItem = MutableStateFlow<Item?>(null)
    val selectedItem: StateFlow<Item?> = _selectedItem.asStateFlow()

    fun selectItem(item: Item) {
        _selectedItem.value = item
    }

    fun createItem(course: String, score: String) = viewModelScope.launch {
        val userId = authRepo.currentUser()?.uid ?: return@launch
        val newItem = Item(
            userId = userId,
            course = course,
            score = score,
            createdAt = System.currentTimeMillis()
        )
        firestore.createItem(newItem)
    }

    fun updateSelectedItem(course: String, score: String) = viewModelScope.launch {
        val currentItem = _selectedItem.value ?: return@launch
        val updates = mapOf(
            "course" to course,
            "score" to score
        )
        firestore.updateItem(currentItem.documentId, updates)
    }

    fun deleteItem(itemId: String) = viewModelScope.launch {
        firestore.deleteItem(itemId)
    }

    fun clearSelectedItem() {
        _selectedItem.value = null
    }
}