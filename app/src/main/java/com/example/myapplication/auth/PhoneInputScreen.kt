package com.example.melodytest

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneInputScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current as ComponentActivity

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
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number", color = Color.White) }, // White text color
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White), // White text color for input text
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White, // White cursor color
                    focusedBorderColor = Color.White, // White outline when focused
                    unfocusedBorderColor = Color.White, // White outline when unfocused
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.verifyPhoneNumber(phoneNumber, context) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Send OTP", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (authState is AuthState.CodeSent || authState is AuthState.Success) {
                OtpVerificationScreen(navController = navController, viewModel = viewModel)
            }

            if (authState is AuthState.Success) {
                LaunchedEffect(authState) {
                    navController.navigate("success")
                }
            } else if (authState is AuthState.Error) {
                Text("Error: ${(authState as AuthState.Error).message}", color = Color.White)
            }
        }
    }
}
