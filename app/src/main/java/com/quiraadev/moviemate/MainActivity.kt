package com.quiraadev.moviemate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.quiraadev.moviemate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
	private val binding: ActivityMainBinding by viewBinding()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_navhost) as NavHostFragment
		val navController = navHostFragment.navController
		binding.bottomNavbar.setupWithNavController(navController)
	}
}