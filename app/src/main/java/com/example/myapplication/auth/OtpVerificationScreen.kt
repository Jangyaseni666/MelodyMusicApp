package com.example.myapplication.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    var otpCode by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black // Set black background color for the entire screen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = otpCode,
                onValueChange = { otpCode = it },
                label = { Text("OTP Code", color = Color.White) }, // White text color
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White), // White text color for input text
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White, // White cursor color
                    focusedBorderColor = Color.White, // White outline when focused
                    unfocusedBorderColor = Color.White, // White outline when unfocused
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {viewModel.verifyCode(otpCode)  },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Verify Code", color = Color.White)
            }

            when (authState) {
                is AuthState.Success -> {
                    LaunchedEffect(authState) {
                        navController.navigate("home")
                    }
                }
                is AuthState.Error -> {
                    Text("Error: ${(authState as AuthState.Error).message}", color = Color.White)
                }
                else -> {}
            }
        }
    }
}
