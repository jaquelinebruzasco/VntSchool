package com.jaquelinebruzasco.vntschool_exercise_screen.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.jaquelinebruzasco.vntschool_exercise_screen.R
import com.jaquelinebruzasco.vntschool_exercise_screen.databinding.FragmentBottomSheetProductRegistrationBinding
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.ProductRegistrationBSDFragmentViewModel
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.ProductRegistrationState

class ProductRegistrationBSDFragment(
    val refresh: () -> Unit
) : BottomSheetDialogFragment() {

    private val viewModel by viewModels<ProductRegistrationBSDFragmentViewModel>()
    private lateinit var _binding: FragmentBottomSheetProductRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetProductRegistrationBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
        initView()
    }

    private fun initObservables() {
        viewModel.productRegistration.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProductRegistrationState.Idle -> hideProgressBar()
                is ProductRegistrationState.Loading -> showProgressBar()
                is ProductRegistrationState.Success -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.message_product_success),
                        Toast.LENGTH_LONG
                    ).show()
                    refresh.invoke()
                    this@ProductRegistrationBSDFragment.dismiss()
                }
                is ProductRegistrationState.Failure -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.message_product_failure),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ProductRegistrationState.FailureProductName -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.message_product_failure_name),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ProductRegistrationState.FailureProductPrice -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.message_product_failure_price),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initView() {
        _binding.btnSave.setOnClickListener {
            with(viewModel) {
                validateProductRegistration(
                    _binding.etProductName.text.toString(),
                    _binding.etPrice.text.toString()
                )
            }
        }
        _binding.btnDelete.setOnClickListener { this@ProductRegistrationBSDFragment.dismiss() }

    }

    private fun showProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.VISIBLE
            tvName.visibility = View.GONE
            etProductName.visibility = View.GONE
            tvPriceInfo.visibility = View.GONE
            etPriceLayout.visibility = View.GONE
            etPrice.visibility = View.GONE
            btnSave.visibility = View.GONE
            btnDelete.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.GONE
            tvName.visibility = View.VISIBLE
            etProductName.visibility = View.VISIBLE
            tvPriceInfo.visibility = View.VISIBLE
            etPriceLayout.visibility = View.VISIBLE
            etPrice.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
            btnDelete.visibility = View.VISIBLE
        }
    }
}
