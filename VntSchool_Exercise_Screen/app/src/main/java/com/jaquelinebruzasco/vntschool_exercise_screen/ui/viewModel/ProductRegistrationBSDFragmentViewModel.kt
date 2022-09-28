package com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProductRegistrationBSDFragmentViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _productRegistration = MutableLiveData<ProductRegistrationState>(ProductRegistrationState.Idle)
    val productRegistration: LiveData<ProductRegistrationState> = _productRegistration

    fun validateProductRegistration(productName: String, productPrice: String) {
        viewModelScope.launch {
            _productRegistration.value = ProductRegistrationState.Loading
            if (productName.isBlank()) {
                _productRegistration.value = ProductRegistrationState.FailureProductName
            } else {
                if (productPrice.isBlank()) {
                    _productRegistration.value = ProductRegistrationState.FailureProductPrice
                } else {
                    val product = hashMapOf(
                        "productName" to productName,
                        "productPrice" to productPrice
                    )
                    db.collection("products")
                        .add(product)
                        .addOnSuccessListener {
                            _productRegistration.value = ProductRegistrationState.Success
                        }
                        .addOnFailureListener {
                            _productRegistration.value = ProductRegistrationState.Failure
                        }
                }
            }
        }
    }
}

sealed class ProductRegistrationState {
    object Idle : ProductRegistrationState()
    object Loading : ProductRegistrationState()
    object Success : ProductRegistrationState()
    object Failure : ProductRegistrationState()
    object FailureProductName : ProductRegistrationState()
    object FailureProductPrice : ProductRegistrationState()
}