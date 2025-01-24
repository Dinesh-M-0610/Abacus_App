package com.example.abacusapp

data class Message(
    val content: String,
    val answer: String,
    var status: MessageStatus = MessageStatus.UNANSWERED
)


enum class MessageStatus {
    CORRECT, INCORRECT, UNANSWERED
}