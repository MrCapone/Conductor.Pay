package com.orange_infinity.onlinepay.data.network.backend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistratePlaceHolderApi {

    @POST("/signIn")
    fun registry(@Body deviceId: String): Call<String>
}