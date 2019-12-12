package com.orange_infinity.onlinepay.data.network.ecomkassa

import com.orange_infinity.onlinepay.entities.dto.ChequeDto
import com.orange_infinity.onlinepay.entities.dto.SellDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CreateSellPlaceHolderApi {

    @POST("/fiscalorder/v2/2/sell")
    fun createCell(@Body sellDto: SellDto, @Header("Token") token: String): Call<ChequeDto>
}