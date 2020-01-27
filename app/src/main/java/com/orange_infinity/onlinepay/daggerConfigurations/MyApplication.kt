package com.orange_infinity.onlinepay.daggerConfigurations

import android.app.Application
import android.os.StrictMode

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .mainModule(MainModule())
            .build()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        //Printooth.init(this)
    }
}