package com.orange_infinity.onlinepay.useCase

import android.util.Log
import com.orange_infinity.onlinepay.ui.presenter.UpdateLoader
import com.orange_infinity.onlinepay.util.MAIN_TAG
import com.orange_infinity.onlinepay.util.getProgramVersion

class ServerEntryController(private val programUpdater: ProgramUpdater) {

    fun signIn(id: String): Boolean {
        Log.i(MAIN_TAG, "Try to sign in")
        return true
    }

    fun sighUp(id: String) {
        Log.i(MAIN_TAG, "Start to sign up in server")
    }

    fun downloadNewVersion() {
        //programUpdater.updateProgram()
    }

    fun isNeedUpdateProgram(updateLoader: UpdateLoader) {
        programUpdater.isLastVersion(getProgramVersion(), updateLoader)
    }
}