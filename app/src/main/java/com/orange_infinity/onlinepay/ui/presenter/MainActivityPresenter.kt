package com.orange_infinity.onlinepay.ui.presenter

import com.orange_infinity.onlinepay.useCase.TicketManager
import com.orange_infinity.onlinepay.useCase.UpdateController

class MainActivityPresenter(
    val ticketManager: TicketManager,
    val updateController: UpdateController
) {
}