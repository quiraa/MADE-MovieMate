package com.quiraadev.moviemate.presentation.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.quiraadev.core.domain.model.ApplicationThemeMode
import com.quiraadev.core.domain.model.SettingsPreferenceModel
import com.quiraadev.moviemate.R
import com.quiraadev.moviemate.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {

	private val binding: ActivitySettingsBinding by viewBinding()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(binding.settingsToolbar)
		binding.settingsToolbar.setNavigationOnClickListener { finish() }

		binding.themeSwitch.setOnCheckedChangeListener { buttonView, _ ->
			if(buttonView.isChecked) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				SettingsPreferenceModel.isDarkMode = ApplicationThemeMode.IS_DARK_MODE
			} else {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
				SettingsPreferenceModel.isDarkMode = ApplicationThemeMode.IS_LIGHT_MODE
			}
		}

		binding.themeSwitch.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

		binding.crashButton.setOnClickListener {
			FirebaseCrashlytics.getInstance().log("Clicked on button")
			FirebaseCrashlytics.getInstance().setCustomKey("str_key", "some_data")
			throw RuntimeException("Test Crash")
		}
	}
}