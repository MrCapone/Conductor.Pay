package com.orange_infinity.onlinepay.data.network.ecomkassa

import com.orange_infinity.onlinepay.entities.dto.CallGetTokenDto
import com.orange_infinity.onlinepay.entities.dto.TokenDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GetTokenPlaceHolderApi {

    @POST("/fiscalorder/v2/getToken")
    fun getToken(@Body getTokenDto: CallGetTokenDto): Call<TokenDto>
}