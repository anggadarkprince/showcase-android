package com.anggaari.showcase.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.anggaari.showcase.R
import com.anggaari.showcase.databinding.FragmentRegisterBinding
import com.anggaari.showcase.models.errors.ValidationErrorResponse
import com.anggaari.showcase.models.user.UserData
import com.anggaari.showcase.models.user.UserValidation
import com.anggaari.showcase.ui.MainActivity
import com.anggaari.showcase.utils.Constants
import com.anggaari.showcase.utils.NetworkResponse
import com.anggaari.showcase.utils.hideKeyboard
import com.anggaari.showcase.viewmodels.AppViewModel
import com.anggaari.showcase.viewmodels.AuthViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

        binding.textViewTermsConditions.setOnClickListener {
            (activity as AuthActivity).openTermsAndConditions(it)
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
                //password != passwordConfirmation -> {
                //    binding.editTextConfirmPassword.error = "Password confirmation mismatch"
                //    binding.editTextConfirmPassword.requestFocus()
                //}
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
                is NetworkResponse.Success -> {
                    val data: UserData = response.body!!.data
                    appViewModel.saveAccessToken(data.accessToken)
                    appViewModel.saveUser(data.user)

                    Constants.ACCESS_TOKEN = data.accessToken
                    Log.d("Register", Constants.ACCESS_TOKEN)

                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        RegisterFragment::class.toString(),
                        "register() success: " + response.body.toString()
                    )
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
                is NetworkResponse.Error -> {
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
                            val validationType = object : TypeToken<ValidationErrorResponse<UserValidation>>() {}.type
                            val userValidation = Gson().fromJson<ValidationErrorResponse<UserValidation>>(
                                response.errorBody?.charStream(), validationType
                            )
                            val validationError = userValidation.errors

                            if (!validationError.name.isNullOrEmpty()) {
                                binding.editTextName.error = validationError.name.first()
                            }
                            if (!validationError.email.isNullOrEmpty()) {
                                binding.editTextEmailAddress.error = validationError.email.first()
                            }
                            if (!validationError.password.isNullOrEmpty()) {
                                binding.editTextPassword.error = validationError.password.first()
                            }
                            if (!validationError.passwordConfirmation.isNullOrEmpty()) {
                                binding.editTextConfirmPassword.error = validationError.passwordConfirmation.first()
                            }
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                is NetworkResponse.Loading -> {
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