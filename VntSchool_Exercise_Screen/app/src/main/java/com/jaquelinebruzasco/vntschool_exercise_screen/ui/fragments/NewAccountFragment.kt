package com.jaquelinebruzasco.vntschool_exercise_screen.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jaquelinebruzasco.vntschool_exercise_screen.R
import com.jaquelinebruzasco.vntschool_exercise_screen.databinding.FragmentNewAccountBinding
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.NewAccountFragmentViewModel
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.ValidateFieldState

class NewAccountFragment : Fragment() {

    private val viewModel by viewModels<NewAccountFragmentViewModel>()
    private lateinit var _binding: FragmentNewAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewAccountBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservables()
        initView()
    }


    private fun initObservables() {
        viewModel.validateField.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ValidateFieldState.Idle -> hideProgressBar()
                is ValidateFieldState.Loading -> showProgressBar()
                is ValidateFieldState.Valid -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_valid),
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
                is ValidateFieldState.Failure -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_failure),
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is ValidateFieldState.FailureName -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_failure_name),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ValidateFieldState.FailureEmail -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_failure_email),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is ValidateFieldState.FailurePassword -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_failure_password),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun initView() {
        _binding.btnCreateAccount.setOnClickListener {
            with(viewModel) {
                validateNewAccountField(
                    _binding.etName.text.toString(),
                    _binding.etEmail.text.toString(),
                    _binding.etPassword.text.toString()
                )
            }
        }
    }

    private fun showProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.VISIBLE
            tvCreate.visibility = View.GONE
            tvName.visibility = View.GONE
            etName.visibility = View.GONE
            tvEmail.visibility = View.GONE
            etEmail.visibility = View.GONE
            tvPassword.visibility = View.GONE
            etPassword.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.GONE
            tvCreate.visibility = View.VISIBLE
            tvName.visibility = View.VISIBLE
            etName.visibility = View.VISIBLE
            tvEmail.visibility = View.VISIBLE
            etEmail.visibility = View.VISIBLE
            tvPassword.visibility = View.VISIBLE
            etPassword.visibility = View.VISIBLE
        }
    }
}