package com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jaquelinebruzasco.vntschool_exercise_screen.model.ProductModel
import kotlinx.coroutines.launch

class ProductsFragmentViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _collectionObs = MutableLiveData<GetCollectionState>(GetCollectionState.Idle)
    val collectionObs: LiveData<GetCollectionState> = _collectionObs

    private val _deleteDocumentObs = MutableLiveData<DeleteDocumentState>(DeleteDocumentState.Idle)
    val deleteDocumentObs : LiveData<DeleteDocumentState> = _deleteDocumentObs

    fun getCollection() {
        viewModelScope.launch {
            _collectionObs.value = GetCollectionState.Loading
            db.collection("products").get()
                .addOnSuccessListener {
                    val listOfProducts: MutableList<ProductModel> = mutableListOf()
                    it.documents.forEach { doc ->
                        listOfProducts.add(
                            ProductModel(
                                doc.id,
                                doc.data?.get("productName") as String,
                                doc.data?.get("productPrice") as String
                            )
                        )
                    }
                    _collectionObs.value = GetCollectionState.Success(listOfProducts)
                }
                .addOnFailureListener { _collectionObs.value = GetCollectionState.Failure }
        }
    }

    fun deleteDocument(id: String, currentPosition: Int) {
        viewModelScope.launch {
            _deleteDocumentObs.value = DeleteDocumentState.Loading
            db.collection("products").document(id).delete()
                .addOnSuccessListener {
                    _deleteDocumentObs.value = DeleteDocumentState.Success(currentPosition)
                }
                .addOnFailureListener {
                    _deleteDocumentObs.value = DeleteDocumentState.Failure
                }
        }
    }
}

sealed class GetCollectionState {
    object Idle: GetCollectionState()
    object Loading: GetCollectionState()
    class Success(val data: MutableList<ProductModel>) : GetCollectionState()
    object Failure : GetCollectionState()
}

sealed class DeleteDocumentState {
    object Idle: DeleteDocumentState()
    object Loading: DeleteDocumentState()
    class Success(val currentPosition: Int) : DeleteDocumentState()
    object Failure : DeleteDocumentState()
}