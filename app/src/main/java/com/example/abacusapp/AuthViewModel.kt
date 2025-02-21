package com.example.abacusapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var errorMessage: String? by mutableStateOf(null)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false;
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        db.collection("students").document(user.uid).get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val access = document.getBoolean("access") ?: true
                                    if (!access) {
                                        errorMessage = "You don't have access"
                                        FirebaseAuth.getInstance().signOut()
                                    } else {
                                        _isLoggedIn.value = true
                                        onLoginSuccess()
                                    }
                                } else {
                                    errorMessage = "User data not found"
                                }
                            }
                            .addOnFailureListener { e ->
                                errorMessage = e.message
                            }
                    }
                } else {
                    errorMessage = task.exception?.message
                }
            }
    }

    fun register(name: String, email: String, password: String, onLoginSuccess: () -> Unit) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userData = hashMapOf(
                            "name" to name,
                            "level1" to false,
                            "level2" to false,
                            "level3" to false,
                            "level4" to false,
                            "level5" to false,
                            "access" to true
                        )
                        db.collection("students").document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                _isLoading.value = false
                                _isLoggedIn.value = true
                                onLoginSuccess()
                            }
                            .addOnFailureListener { e ->
                                _isLoading.value = false
                                errorMessage = e.message
                            }
                    }
                } else {
                    _isLoading.value = false
                    errorMessage = task.exception?.message
                }
            }
    }

    fun checkLoginStatus() {
        _isLoggedIn.value = FirebaseAuth.getInstance().currentUser != null
    }
}