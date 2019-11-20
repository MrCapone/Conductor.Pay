package com.orange_infinity.onlinepay.daggerConfigurations

import com.orange_infinity.onlinepay.ui.activities.MainActivity
import com.orange_infinity.onlinepay.ui.activities.PayActivity
import com.orange_infinity.onlinepay.ui.activities.SettingsActivity
import dagger.Component

@Component(modules = [MainModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: SettingsActivity)

    fun inject(activity: PayActivity)
}