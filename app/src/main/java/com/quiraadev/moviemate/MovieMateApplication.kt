package com.quiraadev.moviemate

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import com.quiraadev.core.domain.model.ApplicationThemeMode
import com.quiraadev.core.domain.model.SettingsPreferenceModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieMateApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		DynamicColors.applyToActivitiesIfAvailable(this)
		if(SettingsPreferenceModel.isDarkMode == ApplicationThemeMode.IS_DARK_MODE) {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
			return
		}
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
	}
}