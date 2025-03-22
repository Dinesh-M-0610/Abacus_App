package com.example.abacusapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = if (isLogin) "Welcome Back" else "Register", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                if (!isLogin) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color(0xFFEDEDED),
                            unfocusedContainerColor = Color(0xFFEDEDED),
                            disabledContainerColor = Color.LightGray,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        disabledTextColor = Color.Gray,
                        focusedContainerColor = Color(0xFFEDEDED),
                        unfocusedContainerColor = Color(0xFFEDEDED),
                        disabledContainerColor = Color.LightGray,
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        disabledTextColor = Color.Gray,
                        focusedContainerColor = Color(0xFFEDEDED),
                        unfocusedContainerColor = Color(0xFFEDEDED),
                        disabledContainerColor = Color.LightGray,
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    )
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(text = if (isLogin) "Login" else "Register", color = Color.White)
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
    val summarizedError = summarizeError(errorMessage)

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
            Text(text = summarizedError, color = MaterialTheme.colorScheme.error)
        }
    )
}

fun summarizeError(errorMessage: String): String {
    return when {
        "There is no user record" in errorMessage -> "No account found with this email. Please register first."
        "The email address is badly formatted" in errorMessage -> "Invalid email format. Please enter a valid email."
        "The password is invalid" in errorMessage || "The supplied auth credential is incorrect" in errorMessage ->
            "Incorrect password. Please try again."
        "A network error" in errorMessage -> "Network error. Please check your internet connection."
        "The email address is already in use" in errorMessage -> "This email is already registered. Try logging in instead."
        "Password should be at least" in errorMessage -> "Your password is too short. Use at least 6 characters."
        "User disabled" in errorMessage -> "This account has been disabled. Contact support for assistance."
        "TOO_MANY_ATTEMPTS_TRY_LATER" in errorMessage -> "Too many login attempts. Please try again later."
        else -> "An error occurred. Please try again."
    }
}
