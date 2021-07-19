package com.anggaari.showcase.ui.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.anggaari.showcase.R
import com.anggaari.showcase.databinding.FragmentLoginBinding
import com.anggaari.showcase.models.auth.login.LoginData
import com.anggaari.showcase.models.auth.login.LoginResult
import com.anggaari.showcase.ui.MainActivity
import com.anggaari.showcase.utils.Constants
import com.anggaari.showcase.utils.NetworkResult
import com.anggaari.showcase.viewmodels.AppViewModel
import com.anggaari.showcase.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel
    private lateinit var appViewModel: AppViewModel

    private val args: LoginFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        appViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.textViewRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textViewForgotPassword.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPassword.text.toString()

            Log.d(LoginFragment::class.toString(), "Sign in")
            when {
                email.isEmpty() -> {
                    Log.d(LoginFragment::class.toString(), "Email is empty")
                    binding.editTextEmailAddress.error = "Please input your email"
                    binding.editTextEmailAddress.requestFocus()
                }
                password.isEmpty() -> {
                    Log.d(LoginFragment::class.toString(), "Password is empty")
                    binding.editTextPassword.error = "Please input your password"
                    binding.editTextPassword.requestFocus()
                }
                else -> {
                    login(email, password)
                }
            }
        }

        val status = args.status
        val message = args.message

        if (status == "OK" && !message.isNullOrEmpty()) {
            Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_LONG).show()
        }

        return binding.root
    }

    private fun login(email: String, password: String) {
        Log.d(LoginFragment::class.toString(), "login(${email},${password}) called")
        authViewModel.login(email, password)
        authViewModel.loginResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val data: LoginData = response.data!!.data
                    appViewModel.saveAccessToken(data.accessToken)
                    appViewModel.saveUser(data.user)

                    Constants.ACCESS_TOKEN = data.accessToken
                    Log.d("Login",Constants.ACCESS_TOKEN)

                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        LoginFragment::class.toString(),
                        "login() success: " + response.data.toString()
                    )
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        LoginFragment::class.toString(),
                        "login() error: " + response.message.toString()
                    )
                    when(response.code) {
                        404 -> {
                            binding.editTextEmailAddress.error = "Account is not found"
                            binding.editTextEmailAddress.requestFocus()
                        }
                        401 -> {
                            binding.editTextPassword.error = "Email or password is wrong"
                            binding.editTextPassword.requestFocus()
                        }
                    }
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(LoginFragment::class.toString(), "login() loading")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}