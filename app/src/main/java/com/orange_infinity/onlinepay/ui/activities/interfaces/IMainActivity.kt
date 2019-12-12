package com.orange_infinity.onlinepay.ui.activities.interfaces

interface IMainActivity {

    fun onSetupEnded()

    fun onCashPayed(link: String)

    fun onCardPayed(link: String)
}