package com.orange_infinity.onlinepay.useCase

import android.util.Log
import com.orange_infinity.onlinepay.data.network.backend.MyBackendNetworkService
import com.orange_infinity.onlinepay.entities.dto.ChequeDto
import com.orange_infinity.onlinepay.ui.presenter.UpdateLoader
import com.orange_infinity.onlinepay.util.MAIN_TAG
import com.orange_infinity.onlinepay.util.getProgramVersion
import io.reactivex.rxjava3.annotations.NonNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerEntryController(private val programUpdater: ProgramUpdater) {

    fun signIn(deviceId: String): Boolean {
        val json =
        MyBackendNetworkService.getInstance()
            .getRegistratePlaceHolderApi()
            .registry(deviceId)
            .enqueue(object : Callback<String> {

                override fun onResponse(@NonNull call: Call<String>, @NonNull response: Response<String>) {
                    val responseBody = response.body()
                    Log.i(MAIN_TAG, "onResponse for signIn(), deviceId = ${deviceId}")
                }

                override fun onFailure(@NonNull call: Call<String>, @NonNull t: Throwable) {
                    Log.i(MAIN_TAG, "ERROR in sendCashChequeToBackend(): " + t.localizedMessage)
                    t.printStackTrace()
                }
            })
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