package ru.netology.cryptotracker

import android.app.Application
import ru.netology.cryptotracker.data.network.CoinApiFactory

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CoinApiFactory.init(this)
    }
}