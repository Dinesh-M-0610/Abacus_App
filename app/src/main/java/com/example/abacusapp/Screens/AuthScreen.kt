package com.example.abacusapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(authViewModel: AuthViewModel, onLoginSuccess: () -> Unit, onAdminClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }

    val error = authViewModel.errorMessage
    val isLoading by authViewModel.isLoading.collectAsState()

    if(isLoading){
        LoadingScreen()
    }
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),  // Centers the login/register form
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (isLogin) "Login" else "Register", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                if (!isLogin) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (isLogin) {
                            authViewModel.login(email, password, onLoginSuccess)
                        } else {
                            authViewModel.register(name, email, password, onLoginSuccess)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isLogin) "Login" else "Register")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { isLogin = !isLogin },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isLogin) "Need to register?" else "Already have an account?")
                }
                if (error != null) {
                    ErrorDialog(errorMessage = error, onDismiss = { authViewModel.errorMessage = null })
                }
            }

            TextButton(
                onClick = onAdminClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Admin Login", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Error", color = MaterialTheme.colorScheme.error)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.error)
                }
            }
        },
        text = {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    )
}