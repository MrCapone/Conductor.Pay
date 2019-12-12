package com.orange_infinity.onlinepay.data.network.backend

import com.orange_infinity.onlinepay.entities.dto.LastVersionDto
import retrofit2.Call
import retrofit2.http.GET

interface LastVersionPlaceHolderApi {

    @GET("/last-version")
    fun getLastVersion(): Call<LastVersionDto>
}