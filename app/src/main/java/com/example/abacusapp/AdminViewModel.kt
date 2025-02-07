package com.example.abacusapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AdminViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    init {
        fetchStudents()
    }

    private fun fetchStudents() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("students").get().await()
                _students.value = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Student::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            try {
                db.collection("students").document(student.id).set(student).await()
                fetchStudents() // Refresh the list
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

//    fun removeStudent(student: Student) {
//        viewModelScope.launch {
//            try {
//                // Delete from Firestore
//                db.collection("students").document(student.id).delete().await()
//
//                // Delete from Authentication
//                try {
//                    val userRecord = auth.getUserByEmail(student.email).await()
//                    if (auth.currentUser?.uid != userRecord.uid) {
//                        // Only delete if the current user is not the one being deleted
//                        auth.deleteUser(userRecord.uid).await()
//                    } else {
//                        throw Exception("Cannot delete the current admin user")
//                    }
//                } catch (e: FirebaseAuthException) {
//                    // Handle the case where the user doesn't exist in Authentication
//                    // This could happen if the user was deleted from Authentication but not from Firestore
//                    println("User not found in Authentication: ${e.message}")
//                }
//
//                fetchStudents() // Refresh the list
//            } catch (e: Exception) {
//                // Handle error
//                println("Error removing student: ${e.message}")
//            }
//        }
//    }
}





