package com.orange_infinity.onlinepay.data.network.backend

import com.orange_infinity.onlinepay.entities.dto.UpdateUrlDto
import retrofit2.Call
import retrofit2.http.GET

interface UpdaterPlaceHolderApi {

    @GET("/update-url")
    fun getUpdaterUrl(): Call<UpdateUrlDto>
}