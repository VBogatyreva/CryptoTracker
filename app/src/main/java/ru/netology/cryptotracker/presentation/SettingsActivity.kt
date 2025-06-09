package ru.netology.cryptotracker.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.cryptotracker.R
import ru.netology.cryptotracker.data.settings.SettingsManager
import ru.netology.cryptotracker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsManager = SettingsManager(this)

        when (settingsManager.currentTheme) {
            SettingsManager.THEME_LIGHT -> binding.themeRadioGroup.check(R.id.lightTheme)
            SettingsManager.THEME_DARK -> binding.themeRadioGroup.check(R.id.darkTheme)
        }

        when (settingsManager.currentLanguage) {
            SettingsManager.LANGUAGE_EN -> binding.languageRadioGroup.check(R.id.englishLanguage)
            SettingsManager.LANGUAGE_RU -> binding.languageRadioGroup.check(R.id.russianLanguage)
        }

        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {

            when (binding.themeRadioGroup.checkedRadioButtonId) {
                R.id.lightTheme -> settingsManager.currentTheme = SettingsManager.THEME_LIGHT
                R.id.darkTheme -> settingsManager.currentTheme = SettingsManager.THEME_DARK
            }

            when (binding.languageRadioGroup.checkedRadioButtonId) {
                R.id.englishLanguage -> settingsManager.currentLanguage = SettingsManager.LANGUAGE_EN
                R.id.russianLanguage -> settingsManager.currentLanguage = SettingsManager.LANGUAGE_RU
            }

            startActivity(CoinPriceListActivity.newIntent(this))
            finish()
        }
    }

    companion object {
        fun newIntent(context: android.content.Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
}