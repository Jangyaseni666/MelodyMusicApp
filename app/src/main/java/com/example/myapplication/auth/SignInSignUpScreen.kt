package com.example.melodytest
/*
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R

@Composable
fun SignInSignUpScreen(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    val state by authViewModel.authState.collectAsStateWithLifecycle()

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
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
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
                Icon(
                    Icons.Filled.PhoneInTalk,
                    contentDescription = "Phone Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Continue with Phone Number", color = Color.White)
            }

            // Continue with Google Button
            GoogleSignInButton(
                authViewModel = authViewModel,
                onSignInResult = { result ->
                    authViewModel.handleGoogleSignInResult(result.data ?: return@GoogleSignInButton)
                },
                isSignInSuccessful = state is AuthState.GoogleSignInSuccess
            )

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

@Composable
fun GoogleSignInButton(
    authViewModel: AuthViewModel,
    onSignInResult: (androidx.activity.result.ActivityResult) -> Unit,
    isSignInSuccessful: Boolean
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            onSignInResult(result)
        }
    )

    // Launch effect to react to successful sign-in
    LaunchedEffect(key1 = isSignInSuccessful) {
        if (isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Button(onClick = {
        authViewModel.signInWithGoogle()
        // This line should be inside the onClick handler of the actual Google Sign-In process
        authViewModel.googleSignInIntentSender?.let { intentSender ->
            launcher.launch(IntentSenderRequest.Builder(intentSender).build())
        }
    }) {
        Text("Sign In with Google")
    }
}
*/