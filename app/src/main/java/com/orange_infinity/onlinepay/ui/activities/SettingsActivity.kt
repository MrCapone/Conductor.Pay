package com.orange_infinity.onlinepay.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.useCase.CashChequeManager
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    @Inject lateinit var cashChequeManager: CashChequeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        (application as MyApplication).appComponent.inject(this)
    }
}