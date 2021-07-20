package com.anggaari.showcase.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.anggaari.showcase.R
import com.anggaari.showcase.databinding.FragmentRegisterBinding
import com.anggaari.showcase.models.auth.login.LoginData
import com.anggaari.showcase.ui.MainActivity
import com.anggaari.showcase.utils.Constants
import com.anggaari.showcase.utils.NetworkResult
import com.anggaari.showcase.utils.hideKeyboard
import com.anggaari.showcase.viewmodels.AppViewModel
import com.anggaari.showcase.viewmodels.AuthViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel
    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        appViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        (activity as AuthActivity).setAuthTitle(
            resources.getString(R.string.register),
            resources.getString(R.string.join_with_thousands_creator),
        )

        binding.textViewLogin.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPassword.text.toString()
            val passwordConfirmation = binding.editTextConfirmPassword.text.toString()

            Log.d(RegisterFragment::class.toString(), "Register")
            when {
                name.isEmpty() -> {
                    binding.editTextName.error = "Please input your name"
                    binding.editTextName.requestFocus()
                }
                email.isEmpty() -> {
                    binding.editTextEmailAddress.error = "Please input your email"
                    binding.editTextEmailAddress.requestFocus()
                }
                password.isEmpty() -> {
                    binding.editTextPassword.error = "Please input your password"
                    binding.editTextPassword.requestFocus()
                }
                password !== passwordConfirmation -> {
                    binding.editTextConfirmPassword.error = "Password confirmation mismatch"
                    binding.editTextConfirmPassword.requestFocus()
                }
                else -> {
                    hideKeyboard()
                    register(name, email, password, passwordConfirmation)
                }
            }
        }

        return binding.root
    }

    private fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        Log.d(
            RegisterFragment::class.toString(),
            "register(${name},${email},${password},${passwordConfirmation}) called"
        )
        authViewModel.register(name, email, password, passwordConfirmation)
        authViewModel.registerResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val data: LoginData = response.data!!.data
                    appViewModel.saveAccessToken(data.accessToken)
                    appViewModel.saveUser(data.user)

                    Constants.ACCESS_TOKEN = data.accessToken
                    Log.d("Register", Constants.ACCESS_TOKEN)

                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        RegisterFragment::class.toString(),
                        "register() success: " + response.data.toString()
                    )
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        RegisterFragment::class.toString(),
                        "register() error: " + response.message.toString()
                    )
                    when (response.code) {
                        404 -> {
                            binding.editTextEmailAddress.error = "Account is not found"
                            binding.editTextEmailAddress.requestFocus()
                        }
                        422 -> {
                            binding.editTextName.error = "Form invalid"
                            binding.editTextName.requestFocus()
                        }
                    }
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(RegisterFragment::class.toString(), "register() loading")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}