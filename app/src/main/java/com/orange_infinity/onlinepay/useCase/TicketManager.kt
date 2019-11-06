package com.orange_infinity.onlinepay.useCase

import android.util.Log
import com.orange_infinity.onlinepay.data.db.TicketRepository
import com.orange_infinity.onlinepay.util.MAIN_TAG

class TicketManager(private val ticketRepository: TicketRepository) {

    fun write() {
        Log.i(MAIN_TAG, "write(), ticketRepository: $ticketRepository")
    }
}