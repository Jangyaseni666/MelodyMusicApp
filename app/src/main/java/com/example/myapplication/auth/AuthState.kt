package com.example.melodytest

import android.content.IntentSender
import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object CodeSent : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
    data class GoogleSignInStarted(val intentSender: IntentSender) : AuthState()
    data class GoogleSignInSuccess(val user: UserData?) : AuthState()
    data class GoogleSignInError(val message: String) : AuthState()
}
