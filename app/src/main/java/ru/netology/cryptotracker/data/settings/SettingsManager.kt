package ru.netology.cryptotracker.data.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class SettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    companion object {
        const val THEME_KEY = "theme"
        const val LANGUAGE_KEY = "language"
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
        const val LANGUAGE_EN = "en"
        const val LANGUAGE_RU = "ru"
    }

    var currentTheme: String
        get() = sharedPreferences.getString(THEME_KEY, THEME_LIGHT) ?: THEME_LIGHT
        set(value) {
            sharedPreferences.edit().putString(THEME_KEY, value).apply()
            applyTheme(value)
        }

    var currentLanguage: String
        get() = sharedPreferences.getString(LANGUAGE_KEY, LANGUAGE_EN) ?: LANGUAGE_EN
        set(value) {
            sharedPreferences.edit().putString(LANGUAGE_KEY, value).apply()
        }

    private fun applyTheme(theme: String) {
        when (theme) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun setupInitialTheme() {
        applyTheme(currentTheme)
    }
}