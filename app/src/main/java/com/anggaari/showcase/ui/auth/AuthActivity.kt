package com.anggaari.showcase.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.anggaari.showcase.R
import com.anggaari.showcase.databinding.ActivityAuthBinding
import com.anggaari.showcase.ui.web.WebViewerActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun setAuthTitle(title: String, subtitle: String) {
        binding.textViewTitle.text = title
        binding.textViewSubtitle.text = subtitle
    }

    fun openTermsAndConditions(view: View) {
        val intent = Intent(this@AuthActivity, WebViewerActivity::class.java)
        intent.putExtra("url", "https://www.angga-ari.com/agreement")
        startActivity(intent)
    }
}