package com.orange_infinity.onlinepay.data.network.ecomkassa

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ECOM_URL = "https://app.ecomkassa.ru"

class EcomNetworkService {

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(ECOM_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private var instance: EcomNetworkService? = null

        fun getInstance(): EcomNetworkService {
            if (instance == null) {
                instance =
                    EcomNetworkService()
            }
            return instance!!
        }
    }

    fun getTokenPlaceHolderApi(): GetTokenPlaceHolderApi {
        return retrofit.create(GetTokenPlaceHolderApi::class.java)
    }

    fun getCreateSellPlaceHolderApi(): CreateSellPlaceHolderApi {
        return retrofit.create(CreateSellPlaceHolderApi::class.java)
    }
}