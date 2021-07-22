package com.anggaari.showcase.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.anggaari.showcase.R
import com.anggaari.showcase.databinding.FragmentForgotPasswordBinding
import com.anggaari.showcase.models.commons.StandardResponse
import com.anggaari.showcase.utils.NetworkResult
import com.anggaari.showcase.utils.hideKeyboard
import com.anggaari.showcase.viewmodels.AuthViewModel

class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        (activity as AuthActivity).setAuthTitle(
            resources.getString(R.string.forgot_password),
            resources.getString(R.string.we_will_send_you_a_reset_link),
        )

        binding.textViewRememberPasswordLogin.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }

        binding.buttonResetPassword.setOnClickListener {
            val email = binding.editTextEmailAddress.text.toString()

            Log.d(ForgotPasswordFragment::class.toString(), "Request reset password link")
            when {
                email.isEmpty() -> {
                    Log.d(ForgotPasswordFragment::class.toString(), "Email is empty")
                    binding.editTextEmailAddress.error = "Please input your email"
                    binding.editTextEmailAddress.requestFocus()
                }
                else -> {
                    hideKeyboard()
                    requestResetPassword(email)
                }
            }
        }

        return binding.root
    }

    private fun requestResetPassword(email: String) {
        Log.d(ForgotPasswordFragment::class.toString(), "requestResetPassword(${email}) called")
        authViewModel.forgotPassword(email)
        authViewModel.forgotPasswordResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val data: StandardResponse = response.data!!

                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        ForgotPasswordFragment::class.toString(),
                        "requestResetPassword() success: " + response.data.toString()
                    )

                    val loginScreen = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment(data.status, data.message)
                    Navigation.findNavController(binding.buttonResetPassword).navigate(loginScreen)
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(
                        ForgotPasswordFragment::class.toString(),
                        "requestResetPassword() error: " + response.message.toString()
                    )
                    when(response.code) {
                        404, 400 -> {
                            binding.editTextEmailAddress.error = "Account is not found"
                            binding.editTextEmailAddress.requestFocus()
                        }
                    }
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(ForgotPasswordFragment::class.toString(), "requestResetPassword() loading")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}