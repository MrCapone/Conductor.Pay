package com.orange_infinity.onlinepay.ui.presenter

import android.os.Build
import android.util.Log
import com.orange_infinity.onlinepay.useCase.TicketManager
import com.orange_infinity.onlinepay.useCase.ServerEntryController
import com.orange_infinity.onlinepay.util.MAIN_TAG

class MainActivityPresenter(
    val ticketManager: TicketManager,
    val serverEntryController: ServerEntryController
) {

    // TODO("Change pseudoId to IMEI")
    fun sendSignInInfoToServer() {
        val pseudoID = "35${Build.BOARD.length % 10}${Build.BRAND.length % 10}" +
                "${Build.SUPPORTED_ABIS.size % 10}${Build.DEVICE.length % 10}" +
                "${Build.DISPLAY.length % 10}${Build.HOST.length % 10}" +
                "${Build.ID.length % 10}${Build.MANUFACTURER.length % 10}" +
                "${Build.MODEL.length % 10}${Build.PRODUCT.length % 10}" +
                "${Build.TAGS.length % 10}${Build.TYPE.length % 10}" +
                "${Build.USER.length % 10}"
        Log.i(MAIN_TAG, "pseudoId: $pseudoID")

        if (serverEntryController.signIn(pseudoID)) {
            serverEntryController.sighUp(pseudoID)
        }
    }

    fun updateProgram() {
        if (serverEntryController.isNeedUpdateProgram()) {
            serverEntryController.downloadNewVersion()
        }
    }
}