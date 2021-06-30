package com.anggaari.showcase.ui

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anggaari.showcase.R
import com.anggaari.showcase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment,
                R.id.skillFragment,
                R.id.portfolioFragment,
                R.id.awardFragment,
                R.id.educationFragment,
            )
        )

        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            binding.fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.purple_500, null))
            binding.fab.foregroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white, null))

            binding.bottomNavigationView.selectedItemId = R.id.profileFragment
            val profileMenu = binding.bottomNavigationView.menu.findItem(R.id.profileFragment)
            NavigationUI.onNavDestinationSelected(profileMenu, navController)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            binding.fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.purple_100, null))
            binding.fab.foregroundTintList = ColorStateList.valueOf(resources.getColor(R.color.purple_500, null))

            NavigationUI.onNavDestinationSelected(item, navController)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}