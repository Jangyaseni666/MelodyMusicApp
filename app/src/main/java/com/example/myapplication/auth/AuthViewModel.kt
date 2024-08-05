package com.example.melodytest

import android.content.Intent
import android.content.IntentSender
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AuthViewModel(private val googleAuthClient: GoogleAuthClient) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    var googleSignInIntentSender: IntentSender? = null
        private set

    private lateinit var verificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            _authState.value = AuthState.Error(e.message ?: "Verification failed")
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            this@AuthViewModel.verificationId = verificationId
            this@AuthViewModel.resendToken = token
            _authState.value = AuthState.CodeSent
        }
    }

    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error occurred")
                }
            }
    }

    fun signUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success(auth.currentUser)
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error occurred")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }

    fun verifyPhoneNumber(phoneNumber: String, activity: ComponentActivity) {
        _authState.value = AuthState.Loading
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            try {
                val result = auth.signInWithCredential(credential).await()
                val user = result.user
                _authState.value = AuthState.Success(user)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            try {
                val intentSender = googleAuthClient.signIn()
                if (intentSender != null) {
                    googleSignInIntentSender = intentSender
                    _authState.value = AuthState.GoogleSignInStarted(intentSender)
                } else {
                    _authState.value = AuthState.GoogleSignInError("Failed to start Google sign-in")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.GoogleSignInError(e.message ?: "Failed to start Google sign-in")
            }
        }
    }

    fun handleGoogleSignInResult(intent: Intent) {
        viewModelScope.launch {
            try {
                val signInResult = googleAuthClient.signInWithIntent(intent)
                if (signInResult.data != null) {
                    _authState.value = AuthState.GoogleSignInSuccess(signInResult.data)
                } else {
                    _authState.value = AuthState.GoogleSignInError(signInResult.errorMessage ?: "Google sign-in failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.GoogleSignInError(e.message ?: "Google sign-in failed")
            }
        }
    }

    fun clearError() {
        _authState.value = AuthState.Idle
    }
}
