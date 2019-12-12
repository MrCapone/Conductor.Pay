package com.orange_infinity.onlinepay.ui.presenter

import com.orange_infinity.onlinepay.ui.activities.interfaces.IMainActivity
import com.orange_infinity.onlinepay.useCase.TicketManager


class MainActivityPresenter(
    val ticketManager: TicketManager
) {

    lateinit var activity: IMainActivity

    fun payByCash() {

    }

    fun payByCard() {

    }

    fun setUpPaymentSystem() {
        activity.onSetupEnded()
    }
}