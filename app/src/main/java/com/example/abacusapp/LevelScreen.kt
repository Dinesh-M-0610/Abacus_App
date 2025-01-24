package com.example.abacusapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelScreen(levelId: Int, onBackPressed: () -> Unit, levelViewModel: LevelViewModel) {
    LaunchedEffect(levelId) {
        levelViewModel.loadMessages(levelId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Level $levelId") },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(levelViewModel.messages) { message ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { levelViewModel.selectMessage(message) },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (message.status) {
                            MessageStatus.CORRECT -> Color.Green.copy(alpha = 0.1f)
                            MessageStatus.INCORRECT -> Color.Red.copy(alpha = 0.1f)
                            MessageStatus.UNANSWERED -> MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Text(text = message.content, modifier = Modifier.padding(16.dp))
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = "Selected message: ${levelViewModel.selectedMessage?.content ?: "None"}")
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = levelViewModel.userAnswer,
                onValueChange = { levelViewModel.userAnswer = it },
                label = { Text("Your answer") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { levelViewModel.submitAnswer() },
                modifier = Modifier.fillMaxWidth(),
                enabled = levelViewModel.selectedMessage != null
            ) {
                Text("Submit")
            }

            if (levelViewModel.feedback.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = levelViewModel.feedback,
                    color = if (levelViewModel.feedback == "Correct!") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}





