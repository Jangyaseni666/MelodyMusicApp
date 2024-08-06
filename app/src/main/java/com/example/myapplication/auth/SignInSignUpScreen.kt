package com.example.myapplication.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R

@Composable
fun SignInSignUpScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        color = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo and Welcome Text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Center align content
                modifier = Modifier.padding(vertical = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.melody),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Just keep on vibin’",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Sign Up Button
            Button(
                onClick = { navController.navigate("register") }, // Navigate to RegisterScreen
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Sign Up", color = Color.White)
            }

            // Continue with Phone Number Button
            OutlinedButton(
                onClick = { navController.navigate("auth") },
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.phone_icon), // Replace with your image resource
                    contentDescription = "Phone Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Continue with Phone Number", color = Color.White)
            }
/*
            // Continue with Google Button
            OutlinedButton(
                onClick = { /* Handle Continue with Google */ },
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google Icon",
                    tint = Color.Unspecified, // Use Color.Unspecified to keep original colors
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Continue with Google", color = Color.White)
            }

 */

            // Login Text with Navigation
            val annotatedText = buildAnnotatedString {
                pushStyle(SpanStyle(color = Color.White, textDecoration = TextDecoration.Underline))
                append("Login")
                pop()
            }
            ClickableText(
                text = annotatedText,
                onClick = { navController.navigate("signin") }, // Navigate to SignInScreen
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
