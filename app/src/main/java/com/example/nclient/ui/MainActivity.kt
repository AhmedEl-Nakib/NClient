package com.example.nclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.nclient.R
import com.example.nclient.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    lateinit var navHostFragment : NavHostFragment
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeNavId -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.favoriteNavId -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.cartNavId -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.notificationNavId -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        if(navController.currentDestination!!.label == "fragment_home"){
            finish()
        }else{
            super.onBackPressed()
        }
    }
}