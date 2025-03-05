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
    private val db = FirebaseFirestore.getInstance()

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchStudents()
    }

    private fun fetchStudents() {
        viewModelScope.launch {
            _isLoading.value = true;
            try {
                val snapshot = db.collection("students").get().await()
                _students.value = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Student::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                // Handle error
            }
            finally{
                _isLoading.value = false
            }
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                db.collection("students").document(student.id).set(student).await()
                fetchStudents()
            } catch (e: Exception) {
                // Handle error
            }
            finally {
                _isLoading.value = false
            }
        }
    }

    fun removeStudent(student: Student) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                db.collection("students").document(student.id)
                    .update("access", false)
                    .await()
                fetchStudents()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun allowStudent(student: Student) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                db.collection("students").document(student.id)
                    .update("access", true)
                    .await()
                fetchStudents()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}