package com.example.abacusapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LevelViewModel : ViewModel() {
    var messages: List<Message> by mutableStateOf(emptyList())
    var selectedMessage: Message? by mutableStateOf(null)
    var userAnswer: String by mutableStateOf("")
    var feedback: String by mutableStateOf("")

    fun loadMessages(levelId: Int) {
        messages = when (levelId) {
            1 -> listOf(
                Message("What is 2 + 2?", "4"),
                Message("What is 3 + 5?", "8")
            )
            2 -> listOf(
                Message("What is 5 * 3?", "15"),
                Message("What is 12 / 4?", "3")
            )
            3 -> listOf(
                Message("What is 8 + 7?", "15"),
                Message("What is 6 * 9?", "54")
            )
            4 -> listOf(
                Message("What is 10 - 2?", "8"),
                Message("What is 7 + 4?", "11")
            )
            5 -> listOf(
                Message("What is 25 / 5?", "5"),
                Message("What is 14 + 3?", "17")
            )
            else -> emptyList()
        }
    }

    fun selectMessage(message: Message) {
        selectedMessage = message
        userAnswer = ""
        feedback = ""
    }

    fun submitAnswer() {
        val currentMessage = selectedMessage
        if (currentMessage != null && userAnswer.equals(currentMessage.answer, ignoreCase = true)) {
            feedback = "Correct!"
            currentMessage.status = MessageStatus.CORRECT
        } else {
            feedback = "Incorrect. Try again."
            currentMessage?.status = MessageStatus.INCORRECT
        }
        userAnswer = ""
    }
}



