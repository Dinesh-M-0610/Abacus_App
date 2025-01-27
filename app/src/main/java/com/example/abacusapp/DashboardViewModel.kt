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

class DashboardViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _levels = MutableStateFlow<List<Level>>(emptyList())
    val levels: StateFlow<List<Level>> = _levels.asStateFlow()

    init {
        fetchLevels()
    }

    private fun fetchLevels() {
        viewModelScope.launch {
            try {
                val user = auth.currentUser
                if (user != null) {
                    val studentDoc = db.collection("students").document(user.uid).get().await()
                    _levels.value = listOf(
                        Level(1, "Level 1", studentDoc.getBoolean("level1") ?: false),
                        Level(2, "Level 2", studentDoc.getBoolean("level2") ?: false),
                        Level(3, "Level 3", studentDoc.getBoolean("level3") ?: false),
                        Level(4, "Level 4", studentDoc.getBoolean("level4") ?: false),
                        Level(5, "Level 5", studentDoc.getBoolean("level5") ?: false)
                    )
                }
            } catch (e: Exception) {
                println("Error fetching levels: ${e.message}")
            }
        }
    }
}




