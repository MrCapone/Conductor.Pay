package com.orange_infinity.onlinepay.useCase

import android.util.Log
import com.orange_infinity.onlinepay.data.network.UpdateNetworkService
import com.orange_infinity.onlinepay.entities.dto.LastVersionDto
import com.orange_infinity.onlinepay.entities.dto.UpdateUrlDto
import com.orange_infinity.onlinepay.ui.presenter.UpdateLoader
import com.orange_infinity.onlinepay.util.MAIN_TAG
import io.reactivex.rxjava3.annotations.NonNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramUpdater { //TODO("Не правильная архитекрута, хули тут делает класс, работающий с сетью?")

    fun isLastVersion(currentVersion: Int, updateLoader: UpdateLoader) {
        UpdateNetworkService.getInstance()
            .getLastVersionPlaceHolderApi()
            .getLastVersion()
            .enqueue(object : Callback<LastVersionDto> {

                override fun onResponse(@NonNull call: Call<LastVersionDto>, @NonNull response: Response<LastVersionDto>) {
                    val lastVersionDto = response.body()
                    Log.i(MAIN_TAG, "onResponse for isLastVersion")

                    if (lastVersionDto != null) {
                        if (currentVersion != Integer.parseInt(lastVersionDto.version)) {
                            //updateLoader.update()
                            updateProgram(updateLoader)
                            Log.i(MAIN_TAG, "Update from versionCode $currentVersion to ${lastVersionDto.version}")
                        } else {
                            Log.i(MAIN_TAG, "Current version is last, versionCode = ${lastVersionDto.version}")
                            updateLoader.processCurrentVerIsLast()
                        }
                    } else {
                        Log.e(MAIN_TAG, "lastVersionDto is null, error!")
                    }
                }

                override fun onFailure(@NonNull call: Call<LastVersionDto>, @NonNull t: Throwable) {
                    Log.i(MAIN_TAG, "ERROR: " + t.localizedMessage)
                    t.printStackTrace()
                }
            })
    }

    fun updateProgram(updateLoader: UpdateLoader) {
        UpdateNetworkService.getInstance()
            .getUpdaterPlaceHolderApi()
            .getUpdaterUrl()
            .enqueue(object : Callback<UpdateUrlDto> {

                override fun onResponse(@NonNull call: Call<UpdateUrlDto>, @NonNull response: Response<UpdateUrlDto>) {
                    val lastVersionDto = response.body()
                    Log.i(MAIN_TAG, "onResponse for isLastVersion")

                    if (lastVersionDto != null) {
                        Log.i(MAIN_TAG, "Update from url ${lastVersionDto.updateUrl}")
                        updateLoader.update(lastVersionDto.updateUrl)
                    } else {
                        Log.e(MAIN_TAG, "lastVersionDto is null, error!")
                    }
                }

                override fun onFailure(@NonNull call: Call<UpdateUrlDto>, @NonNull t: Throwable) {
                    Log.i(MAIN_TAG, "ERROR: " + t.localizedMessage)
                    t.printStackTrace()
                }
            })
    }
}