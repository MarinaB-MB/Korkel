package com.example.client.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.client.R
import com.example.client.utils.LOGIN
import com.example.client.utils.PASSWORD
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.extras?.getString(LOGIN)
        val password = intent.extras?.getString(PASSWORD)
        bundle.let {
            it.putString(LOGIN, email)
            it.putString(PASSWORD, password)
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_repos, R.id.navigation_favorite, R.id.navigation_about
            )
        )

        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener { item ->
            if (!item.isChecked) {
                navController.navigate(item.itemId, bundle)
            }
            true
        }
    }
}
