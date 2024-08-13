package com.rakarguntara.readgood.viewmodel.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rakarguntara.readgood.network.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading get() = _loading

    fun signIn(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        home()
                        Log.d("Login Success", "createAccount: ${task.result}")
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            Log.d("Login Failed", "Invalid credentials: ${exception.message}")
                        } else if (exception is FirebaseAuthInvalidUserException) {
                            Log.d("Login Failed", "User not found: ${exception.message}")
                        } else {
                            // Kesalahan lain
                            Log.d("Login Failed", "Error: ${exception?.message}")
                        }
                    }

                }
        }catch (e: Exception){
            Log.d("Register Exception", "createAccount: ${e.message}")
        }
    }

    fun createAccount(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        if(_loading.value  == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        home()
                        Log.d("Register Success", "createAccount: ${task.result}")
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            Log.d("Register Failed", "Invalid credentials: ${exception.message}")
                        } else if (exception is FirebaseAuthInvalidUserException) {
                            Log.d("Register Failed", "User not found: ${exception.message}")
                        } else {
                            // Kesalahan lain
                            Log.d("Register Failed", "Error: ${exception?.message}")
                        }
                    }
                    _loading.value = false
                }
        }
    }
}