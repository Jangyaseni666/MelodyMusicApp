package com.example.myapplication.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val dialogState = remember { mutableStateOf(false) }
    val errorDialogState = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black // Set black background color for the entire screen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                )
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (password == confirmPassword) {
                        viewModel.signUp(email, password)
                    } else {
                        errorMessage = "Passwords do not match"
                        errorDialogState.value = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Dialog for successful registration
            if (dialogState.value) {
                AlertDialog(
                    onDismissRequest = {
                        dialogState.value = false
                        navController.navigate("sign_in_sign_up") // Navigate to sign-in/sign-up screen
                    },
                    title = {
                        Text("Registration Successful")
                    },
                    text = {
                        Text("You have successfully registered. Now you can sign in.")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                dialogState.value = false
                                navController.navigate("sign_in_sign_up")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("OK")
                        }
                    }
                )
            }

            // Dialog for error message
            if (errorDialogState.value) {
                AlertDialog(
                    onDismissRequest = {
                        errorDialogState.value = false
                        viewModel.clearError()
                    },
                    title = {
                        Text("Error")
                    },
                    text = {
                        Text(errorMessage)
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                errorDialogState.value = false
                                viewModel.clearError()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Try Again")
                        }
                    }
                )
            }

            val authState by viewModel.authState.collectAsState()
            when (authState) {
                is AuthState.Loading -> CircularProgressIndicator(color = Color.White)
                is AuthState.Success -> {
                    dialogState.value = true
                }
                is AuthState.Error -> {
                    errorMessage = (authState as AuthState.Error).message
                    errorDialogState.value = true
                }
                else -> {}
            }
        }
    }
}
