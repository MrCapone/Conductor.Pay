package com.orange_infinity.onlinepay.daggerConfigurations

import android.app.Application

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .mainModule(MainModule())
            .build()
    }
}