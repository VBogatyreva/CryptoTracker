package ru.netology.cryptotracker

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import ru.netology.cryptotracker.data.settings.LocaleHelper
import ru.netology.cryptotracker.data.settings.SettingsManager
import java.util.Locale

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        SettingsManager(this).setupInitialTheme()
    }

    override fun attachBaseContext(base: Context) {
        val settingsManager = SettingsManager(base)
        super.attachBaseContext(
            LocaleHelper.setLocale(base, settingsManager.currentLanguage).also {
                Locale.setDefault(Locale(settingsManager.currentLanguage))
            }
        )
    }
}