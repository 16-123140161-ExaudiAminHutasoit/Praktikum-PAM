package com.example.myprofilapp

import android.app.Application
import com.example.myprofilapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyProfilApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyProfilApp)
            modules(appModule)
        }
    }
}
