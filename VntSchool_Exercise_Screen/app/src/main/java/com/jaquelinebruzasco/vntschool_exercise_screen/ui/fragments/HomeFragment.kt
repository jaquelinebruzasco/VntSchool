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
import com.jaquelinebruzasco.vntschool_exercise_screen.databinding.FragmentHomeBinding
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.HomeFragmentViewModel
import com.jaquelinebruzasco.vntschool_exercise_screen.ui.viewModel.HomeState

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var _binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservables()
    }

    private fun initObservables() {
        viewModel.validateData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is HomeState.Idle -> hideProgressBar()
                is HomeState.Loading -> showProgressBar()
                is HomeState.Valid -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_authentication_valid),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is HomeState.Failure -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_authentication_failure),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is HomeState.FailureEmail -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_failure_email),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is HomeState.FailurePassword -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_failure_password),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is HomeState.ValidReset -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_reset_success),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is HomeState.FailureReset -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireView(), resources.getString(R.string.message_reset_failure),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun initView() {
        _binding.apply {
            tvCreate.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_newAccountFragment)
            }
            btnLogin.setOnClickListener {
                with(viewModel) {
                    validateDataCollection(
                        _binding.etEmail.text.toString(),
                        _binding.etPassword.text.toString()
                    )
                }
                findNavController().navigate(R.id.action_homeFragment_to_productsFragment)
            }
            tvForget.setOnClickListener {
                with(viewModel) {
                    resetPassword(_binding.etEmail.text.toString())
                }
            }
        }
    }

    private fun showProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.VISIBLE
            tvLogin.visibility = View.GONE
            etEmail.visibility = View.GONE
            etPassword.visibility = View.GONE
            btnLogin.visibility = View.GONE
            tvForget.visibility = View.GONE
            tvCreate.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
        _binding.apply {
            progressCircular.visibility = View.GONE
            tvLogin.visibility = View.VISIBLE
            etEmail.visibility = View.VISIBLE
            etPassword.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            tvForget.visibility = View.VISIBLE
            tvCreate.visibility = View.VISIBLE
        }
    }
}