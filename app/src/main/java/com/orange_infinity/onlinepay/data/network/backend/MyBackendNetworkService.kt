package com.orange_infinity.onlinepay.data.network.backend

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//private const val MY_BACKEND_URL = "http://kolyanpie.ddns.net:8095"
private const val MY_BACKEND_URL = "http://212.192.123.3:8090"
//private const val MY_BACKEND_URL = "http://601625ac.ngrok.io:8090"

class MyBackendNetworkService {

    private var retrofit: Retrofit

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl(MY_BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        private var instance: MyBackendNetworkService? = null

        fun getInstance(): MyBackendNetworkService {
            if (instance == null) {
                instance =
                    MyBackendNetworkService()
            }
            return instance!!
        }
    }

    fun getLastVersionPlaceHolderApi(): LastVersionPlaceHolderApi {
        return retrofit.create(LastVersionPlaceHolderApi::class.java)
    }

    fun getUpdaterPlaceHolderApi(): UpdaterPlaceHolderApi {
        return retrofit.create(UpdaterPlaceHolderApi::class.java)
    }

    fun getCashChequePlaceHolderApi(): CashChequePlaceHolderApi {
        return retrofit.create(CashChequePlaceHolderApi::class.java)
    }

    fun getRegistratePlaceHolderApi(): RegistratePlaceHolderApi {
        return retrofit.create(RegistratePlaceHolderApi::class.java)
    }
}