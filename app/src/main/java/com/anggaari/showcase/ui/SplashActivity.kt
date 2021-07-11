package com.anggaari.showcase.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.anggaari.showcase.R
import com.anggaari.showcase.ui.auth.AuthActivity
import com.anggaari.showcase.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        appViewModel.accessToken.asLiveData().observe(this, { value ->
            appViewModel.setAccessToken(value)
            Handler(Looper.getMainLooper()).postDelayed({
                if (value.isNullOrEmpty()) {
                    startActivity(Intent(this, AuthActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                finish()
            }, 800);
        })
    }
}