package com.orange_infinity.onlinepay.data.network.backend

import com.orange_infinity.onlinepay.entities.dto.ChequeDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CashChequePlaceHolderApi {

    @POST("/cheque/cash/create")
    fun saveCheque(@Body chequeDto: ChequeDto): Call<ChequeDto>
}