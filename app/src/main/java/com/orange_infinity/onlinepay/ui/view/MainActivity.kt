package com.orange_infinity.onlinepay.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.useCase.TicketManager
import com.orange_infinity.onlinepay.useCase.UpdateController
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var ticketManager: TicketManager
    @Inject lateinit var updateController: UpdateController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MyApplication).appComponent.inject(this)
        ticketManager.write()
    }
}
