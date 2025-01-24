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

//class AuthViewModel : ViewModel() {
//    private val auth = FirebaseAuth.getInstance()
//    private val db = FirebaseFirestore.getInstance()
//
//    var errorMessage: String? by mutableStateOf(null)
//
//    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    onLoginSuccess()
//                } else {
//                    errorMessage = task.exception?.message
//                }
//            }
//    }
//
//    fun register(name: String, email: String, password: String, onLoginSuccess: () -> Unit) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                    if (user != null) {
//                        val userData = hashMapOf(
//                            "name" to name,
//                            "level1" to false,
//                            "level2" to false,
//                            "level3" to false,
//                            "level4" to false,
//                            "level5" to false
//                        )
//                        db.collection("students").document(user.uid)
//                            .set(userData)
//                            .addOnSuccessListener {
//                                onLoginSuccess()
//                            }
//                            .addOnFailureListener { e ->
//                                errorMessage = e.message
//                            }
//                    }
//                } else {
//                    errorMessage = task.exception?.message
//                }
//            }
//    }
//}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var errorMessage: String? by mutableStateOf(null)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun login(email: String, password: String, onLoginSuccess: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isLoggedIn.value = true
                    onLoginSuccess()
                } else {
                    errorMessage = task.exception?.message
                }
            }
    }

    fun register(name: String, email: String, password: String, onLoginSuccess: () -> Unit) {
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
                            "level5" to false
                        )
                        db.collection("students").document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                _isLoggedIn.value = true
                                onLoginSuccess()
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

    fun checkLoginStatus() {
        _isLoggedIn.value = FirebaseAuth.getInstance().currentUser != null
    }
}



