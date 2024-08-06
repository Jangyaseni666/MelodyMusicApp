package com.example.myapplication.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthNavigation(viewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "sign_in_sign_up") {
        composable("sign_in_sign_up") {
            SignInSignUpScreen(navController = navController)
        }
        composable("auth") {
            PhoneInputScreen(navController = navController, viewModel = viewModel)
        }
        composable("otpVerification") {
            OtpVerificationScreen(navController = navController, viewModel = viewModel)
        }
        composable("success") {
            SuccessScreen(navController = navController, viewModel = viewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, viewModel = viewModel)
        }
        composable("signin") {
            SignInScreen(navController = navController, viewModel = viewModel)
        }
    }
}