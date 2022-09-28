package com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class NewAccountFragmentViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private val _validateField = MutableLiveData<ValidateFieldState>(ValidateFieldState.Idle)
    val validateField: LiveData<ValidateFieldState> = _validateField

    fun validateNewAccountField(name: String, email: String, password: String) {
        viewModelScope.launch {
            _validateField.value = ValidateFieldState.Loading
            if (validateNameField(name)) {
                if (validateEmailField(email)) {
                    if (validatePasswordField(password)) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    _validateField.value = ValidateFieldState.Valid
                                } else {
                                    _validateField.value = ValidateFieldState.Failure
                                }
                            }
                    } else {
                        _validateField.value = ValidateFieldState.FailurePassword
                    }

                } else {
                    _validateField.value = ValidateFieldState.FailureEmail
                }

            } else {
                _validateField.value = ValidateFieldState.FailureName
            }
        }

    }

    private fun validateNameField(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun validateEmailField(email: String): Boolean {
        return if (email.isNotBlank()) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    private fun validatePasswordField(password: String): Boolean {
        return if (password.isNotBlank()) {
            password.length >= 8
        } else {
            false
        }
    }
}

sealed class ValidateFieldState {
    object Idle : ValidateFieldState()
    object Loading : ValidateFieldState()
    object Valid : ValidateFieldState()
    object Failure : ValidateFieldState()
    object FailureName : ValidateFieldState()
    object FailureEmail : ValidateFieldState()
    object FailurePassword : ValidateFieldState()
}