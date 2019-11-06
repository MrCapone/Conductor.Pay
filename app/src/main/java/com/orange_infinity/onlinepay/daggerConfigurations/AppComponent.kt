package com.orange_infinity.onlinepay.daggerConfigurations

import com.orange_infinity.onlinepay.ui.view.MainActivity
import com.orange_infinity.onlinepay.ui.view.PayActivity
import com.orange_infinity.onlinepay.ui.view.SettingsActivity
import dagger.Component

@Component(modules = [MainModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: SettingsActivity)

    fun inject(activity: PayActivity)
}