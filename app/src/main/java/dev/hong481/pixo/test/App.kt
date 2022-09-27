package dev.hong481.pixo.test

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        const val TAG: String = "App"
    }

    override fun onCreate() {
        super.onCreate()
        initApp()
    }

    private fun initApp() {
        Log.d(TAG, "initApp.")
    }

}