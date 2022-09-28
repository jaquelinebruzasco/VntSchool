package com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private val _validateData = MutableLiveData<HomeState>(HomeState.Idle)
    val validateData: LiveData<HomeState> = _validateData

    fun validateDataCollection(email: String, password: String) {
        viewModelScope.launch {
            _validateData.value = HomeState.Loading
            if (validateEmailData(email)) {
                if (validatePasswordData(password)) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _validateData.value = HomeState.Valid
                            } else {
                                _validateData.value = HomeState.Failure
                            }
                        }
                } else {
                    _validateData.value = HomeState.FailurePassword
                }
            } else {
                _validateData.value = HomeState.FailureEmail
            }
        }

    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _validateData.value = HomeState.Loading
            if (validateEmailData(email)) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _validateData.value = HomeState.ValidReset
                        } else {
                            _validateData.value = HomeState.FailureReset
                        }
                    }

            } else {
                _validateData.value = HomeState.FailureEmail
            }
        }


    }

    private fun validateEmailData(email: String): Boolean {
        return if (email.isNotBlank()) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    private fun validatePasswordData(password: String): Boolean {
        return if (password.isNotBlank()) {
            password.length >= 8
        } else {
            false
        }
    }
}

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    object Valid : HomeState()
    object Failure : HomeState()
    object FailureEmail : HomeState()
    object FailurePassword: HomeState()
    object ValidReset : HomeState()
    object FailureReset : HomeState()
}

