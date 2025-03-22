package com.example.abacusapp

data class Message(
    val content: String,
    val answer: String,
    var audiofile: String = "a1",
    var status: MessageStatus = MessageStatus.UNANSWERED
)


enum class MessageStatus {
    CORRECT, INCORRECT, UNANSWERED
}