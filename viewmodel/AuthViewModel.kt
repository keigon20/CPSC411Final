package com.example.cpsc411final.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cpsc411final.data.auth.AuthRepository
import com.example.cpsc411final.data.model.User
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = repo.authStateFlow()
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.Eagerly, repo.currentUser() != null)

    fun validateEmail(email: String): String? {
        return if (email.isBlank()) "Email required"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email"
        else null
    }

    fun validatePassword(password: String): String? {
        return when {
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }

    fun signUp(email: String, password: String) {
        val emailErr = validateEmail(email)
        val passErr  = validatePassword(password)
        if (emailErr != null || passErr != null) {
            _uiState.update { it.copy(errorMessage = emailErr ?: passErr) }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading=true, errorMessage=null) }
            runCatching { repo.signUp(email, password) }
                .onSuccess { result ->
                    _uiState.update { it.copy(isLoading=false, user = result.getOrNull(), errorMessage = null) }
                }
                .onFailure { exc ->
                    _uiState.update { it.copy(isLoading=false, errorMessage = friendlyMessage(exc)) }
                }
        }
    }

    fun login(email: String, password: String) {
        val emailErr = validateEmail(email)
        val passErr  = validatePassword(password)
        if (emailErr != null || passErr != null) {
            _uiState.update { it.copy(errorMessage = emailErr ?: passErr) }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading=true, errorMessage=null) }
            runCatching { repo.login(email, password) }
                .onSuccess { result ->
                    _uiState.update { it.copy(isLoading=false, user = result.getOrNull(), errorMessage = null) }
                }
                .onFailure { exc ->
                    _uiState.update { it.copy(isLoading=false, errorMessage = friendlyMessage(exc)) }
                }
        }
    }

    fun signOut() = viewModelScope.launch {
        repo.signOut()
        _uiState.update { it.copy(user = null) }
    }

    private fun friendlyMessage(exc: Throwable): String {
        return when (exc) {
            is FirebaseAuthInvalidCredentialsException -> "Invalid credentials"
            is FirebaseAuthInvalidUserException -> "No account found for that email"
            else -> exc.localizedMessage ?: "Unknown error"
        }
    }
}