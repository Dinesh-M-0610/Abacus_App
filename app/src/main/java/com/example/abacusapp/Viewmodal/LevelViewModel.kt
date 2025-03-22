package com.example.abacusapp

import android.content.Context
import android.media.MediaPlayer
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
                Message("Question-1","25","a1"),
                Message("Question-2","23","a2"),
                Message("Question-3","27","a3"),
                Message("Question-4","26","a4"),
                Message("Question-5","25","a5"),
                Message("Question-6","25","a6"),
                Message("Question-7","21","a7"),
                Message("Question-8","28","a8"),
                Message("Question-9","18","a9"),
                Message("Question-10","10","a10"),
                Message("Question-11","16","a11"),
                Message("Question-12","16","a12"),
                Message("Question-13","25","a13"),
                Message("Question-14","24","a14"),
                Message("Question-15","28","a15"),
                Message("Question-16","20","a16"),
                Message("Question-17","30","a17"),
                Message("Question-18","23","a18"),
                Message("Question-19","25","a19"),
                Message("Question-20","25","a20"),
                Message("Question-21","10","a21"),
                Message("Question-22","25","a22"),
                Message("Question-23","8","a23"),
                Message("Question-24","18","a24"),
                Message("Question-25","30","a25"),
                Message("Question-26","24","a26"),
                Message("Question-27","30","a27"),
                Message("Question-28","24","a28"),
                Message("Question-29","20","a29"),
                Message("Question-30","26","a30"),
                Message("Question-31","20","a31"),
                Message("Question-32","31","a32"),
                Message("Question-33","15","a33"),
                Message("Question-34","10","a34"),
                Message("Question-35","9","a35"),
                Message("Question-36","12","a36"),
                Message("Question-37","15","a37"),
                Message("Question-38","33","a38"),
                Message("Question-39","15","a39"),
                Message("Question-40","32","a40")
            )
            2 -> listOf(
                Message("Question-1","28","b1"),
                Message("Question-2","26","b2"),
                Message("Question-3","20","b3"),
                Message("Question-4","28","b4"),
                Message("Question-5","12","b5"),
                Message("Question-6","9","b6"),
                Message("Question-7","1","b7"),
                Message("Question-8","26","b8"),
                Message("Question-9","29","b9"),
                Message("Question-10","17","b10"),
                Message("Question-11","33","b11"),
                Message("Question-12","21","b12"),
                Message("Question-13","30","b13"),
                Message("Question-14","17","b14"),
                Message("Question-15","20","b15"),
                Message("Question-16","31","b16"),
                Message("Question-17","13","b17"),
                Message("Question-18","7","b18"),
                Message("Question-19","6","b19"),
                Message("Question-20","7","b20"),
                Message("Question-21","26","b21"),
                Message("Question-22","26","b22"),
                Message("Question-23","27","b23"),
                Message("Question-24","25","b24"),
                Message("Question-25","25","b25"),
                Message("Question-26","26","b26"),
                Message("Question-27","35","b27"),
                Message("Question-28","15","b28"),
                Message("Question-29","1","b29"),
                Message("Question-30","22","b30"),
                Message("Question-31","9","b31"),
                Message("Question-32","13","b32"),
                Message("Question-33","21","b33"),
                Message("Question-34","28","b34"),
                Message("Question-35","28","b35"),
                Message("Question-36","22","b36"),
                Message("Question-37","28","b37"),
                Message("Question-38","24","b38"),
                Message("Question-39","16","b39"),
                Message("Question-40","31","b40")
            )
            3 -> listOf(
                Message("Question-1","14","c1"),
                Message("Question-2","13","c2"),
                Message("Question-3","10","c3"),
                Message("Question-4","11","c4"),
                Message("Question-5","27","c5"),
                Message("Question-6","27","c6"),
                Message("Question-7","20","c7"),
                Message("Question-8","26","c8"),
                Message("Question-9","29","c9"),
                Message("Question-10","19","c10"),
                Message("Question-11","31","c11"),
                Message("Question-12","23","c12"),
                Message("Question-13","14","c13"),
                Message("Question-14","15","c14"),
                Message("Question-15","13","c15"),
                Message("Question-16","26","c16"),
                Message("Question-17","21","c17"),
                Message("Question-18","25","c18"),
                Message("Question-19","26","c19"),
                Message("Question-20","27","c20"),
                Message("Question-21","23","c21"),
                Message("Question-22","22","c22"),
                Message("Question-23","24","c23"),
                Message("Question-24","1","c24"),
                Message("Question-25","24","c25"),
                Message("Question-26","9","c26"),
                Message("Question-27","17","c27"),
                Message("Question-28","","c28"),
                Message("Question-29","","c29"),
                Message("Question-30","","c30"),
                Message("Question-31","","c31"),
                Message("Question-32","","c32"),
                Message("Question-33","21","c33"),
                Message("Question-34","28","c34"),
                Message("Question-35","28","c35"),
                Message("Question-36","22","c36"),
                Message("Question-37","28","c37"),
                Message("Question-38","24","c38")
            )
            4 -> listOf(
                Message("Question-1","28","d1"),
                Message("Question-2","26","d2"),
                Message("Question-3","20","d3"),
                Message("Question-4","28","d4"),
                Message("Question-5","12","d5"),
                Message("Question-6","9","d6"),
                Message("Question-7","1","d7"),
                Message("Question-8","26","d8"),
                Message("Question-9","29","d9"),
                Message("Question-10","17","d10"),
                Message("Question-11","33","d11"),
                Message("Question-12","21","d12"),
                Message("Question-13","30","d13"),
                Message("Question-14","17","d14"),
                Message("Question-15","20","d15"),
                Message("Question-16","31","d16"),
                Message("Question-17","13","d17"),
                Message("Question-18","7","d18"),
                Message("Question-19","6","d19"),
                Message("Question-20","7","d20"),
                Message("Question-21","26","d21"),
                Message("Question-22","26","d22"),
                Message("Question-23","27","d23"),
                Message("Question-24","25","d24"),
                Message("Question-25","25","d25"),
                Message("Question-26","26","d26"),
                Message("Question-27","35","d27"),
                Message("Question-28","15","d28"),
                Message("Question-29","1","d29"),
                Message("Question-30","22","d30"),
                Message("Question-31","9","d31"),
                Message("Question-32","13","d32"),
                Message("Question-33","21","d33"),
                Message("Question-34","28","d34"),
                Message("Question-35","28","d35"),
                Message("Question-36","22","d36"),
                Message("Question-37","28","d37"),
                Message("Question-38","24","d38"),
                Message("Question-39","16","d39"),
                Message("Question-40","31","d40")
            )
            else -> emptyList()
        }
    }

    fun selectMessage(message: Message, context: Context) {  // ✅ Pass context from UI
        selectedMessage = message
        userAnswer = ""
        feedback = ""
        playAudio(context, message.audiofile)  // ✅ Play the associated audio
    }

    fun submitAnswer(context: Context) {
        val currentMessage = selectedMessage
        if (currentMessage != null && userAnswer.equals(currentMessage.answer, ignoreCase = true)) {
            feedback = "Correct!"
            currentMessage.status = MessageStatus.CORRECT
            playAudio(context, "correct")  // ✅ Play "correct" sound
        } else {
            feedback = "Incorrect. Try again."
            currentMessage?.status = MessageStatus.INCORRECT
            playAudio(context, "wrong")  // ✅ Play "wrong" sound
        }
        userAnswer = ""
    }
}

/**
 * Function to play an audio file from the `res/raw` folder dynamically.
 * @param context The application context.
 * @param fileName The name of the audio file (without extension).
 */
private var mediaPlayer: MediaPlayer? = null  // ✅ Keep a single MediaPlayer instance

fun playAudio(context: Context, fileName: String) {
    val resId = context.resources.getIdentifier(fileName, "raw", context.packageName)

    if (resId != 0) {
        // Stop and release any currently playing audio
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }

        // Create and start a new MediaPlayer instance
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.apply {
            setOnCompletionListener {
                it.release()
                mediaPlayer = null  // ✅ Clear the reference when playback completes
            }
            start()
        }
    } else {
        println("Error: Audio file '$fileName' not found in raw folder.")
    }
}



