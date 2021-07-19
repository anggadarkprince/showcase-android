package com.anggaari.showcase.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.anggaari.showcase.R
import com.anggaari.showcase.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.textViewLogin.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

}