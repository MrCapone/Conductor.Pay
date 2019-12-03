package com.orange_infinity.onlinepay.data.network

import com.orange_infinity.onlinepay.entities.dto.UpdateUrlDto
import retrofit2.Call
import retrofit2.http.GET

interface UpdaterPlaceHolder {

    @GET("/update-url")
    fun getUpdaterUrl(): Call<UpdateUrlDto>
}