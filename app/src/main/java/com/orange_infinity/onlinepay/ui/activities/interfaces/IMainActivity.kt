package com.orange_infinity.onlinepay.ui.activities.interfaces

import android.content.Context

interface IMainActivity {

    fun onSetupEnded()

    //fun onCashPayed(link: String)

    fun onCashPayed()

    fun onCardPayed(link: String)

    fun getAppContext(): Context
}