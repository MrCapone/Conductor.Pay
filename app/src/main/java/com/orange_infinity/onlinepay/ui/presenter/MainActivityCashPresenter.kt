package com.orange_infinity.onlinepay.ui.presenter

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.orange_infinity.onlinepay.data.db.AppDatabase
import com.orange_infinity.onlinepay.data.network.ecomkassa.EcomNetworkService
import com.orange_infinity.onlinepay.entities.dto.CallGetTokenDto
import com.orange_infinity.onlinepay.entities.dto.ChequeDto
import com.orange_infinity.onlinepay.entities.dto.SellDto
import com.orange_infinity.onlinepay.entities.dto.TokenDto
import com.orange_infinity.onlinepay.entities.model.Token
import com.orange_infinity.onlinepay.ui.activities.interfaces.IMainActivity
import com.orange_infinity.onlinepay.useCase.CashChequeManager
import com.orange_infinity.onlinepay.util.MAIN_TAG
import io.reactivex.rxjava3.annotations.NonNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityCashPresenter(
    val cashChequeManager: CashChequeManager
) {

    companion object {
        lateinit var activity: IMainActivity

        private fun buildSellDto(cost: Int): SellDto {
            val sellDto = SellDto()
            val receipt = sellDto.receipt
            receipt.items.first().name += "$cost"
            receipt.items.first().sum = cost.toFloat()
            receipt.payments.first().sum = cost.toFloat()
            receipt.total = cost.toFloat()

            return sellDto
        }

        private fun sendCheque(sellDto: SellDto, cashChequeManager: CashChequeManager, isMainCheque: Boolean) {
            EcomNetworkService.getInstance()
                .getCreateSellPlaceHolderApi()
                .createCell(sellDto, Token.token!!)
                .enqueue(object : Callback<ChequeDto> {

                    override fun onResponse(@NonNull call: Call<ChequeDto>, @NonNull response: Response<ChequeDto>) {
                        val chequeDto = response.body()
                        Log.i(MAIN_TAG, "onResponse for payByCash()")

                        if (chequeDto != null) { //TODO("Send info about cheque in BACKEND")
                            Log.i(MAIN_TAG, "Cheque link: ${chequeDto.permalink}")
                            cashChequeManager.processCashTicket(activity.getAppContext(), chequeDto, sellDto.external_id)
                            if (isMainCheque) {
                                //activity.onCashPayed()
                                UnsetChequeSender().execute(cashChequeManager)
                            }
                        } else {
                            Log.e(MAIN_TAG, "tokenDto is null, error!")
                        }
                    }

                    override fun onFailure(@NonNull call: Call<ChequeDto>, @NonNull t: Throwable) {
                        Log.i(MAIN_TAG, "ERROR: " + t.localizedMessage)
                        t.printStackTrace()
                    }
                })
        }
    }

    fun payByCash(cost: Int) {
        setUpPaymentSystem()

        val sellDto = buildSellDto(cost)
        activity.onCashPayed()
        cashChequeManager.saveChequeByExternalId(activity.getAppContext(), sellDto.external_id, cost)
        sendCheque(sellDto, cashChequeManager, true)
    }

    class UnsetChequeSender : AsyncTask<CashChequeManager, Unit, Unit>() {

        override fun doInBackground(vararg params: CashChequeManager) {
            val cashChequeManager = params[0]
            val unsentCheque = AppDatabase.getInstance(CashChequeManager.applicationContext)
                .getCashChequeDao().findOneChequeTicket(false) ?: return

            val sellDto = buildSellDto(unsentCheque.cost ?: 21)
            sellDto.external_id = unsentCheque.externalId
            sendCheque(sellDto, cashChequeManager, false)
        }

    }

    fun setUpPaymentSystem() {
        // get key from ekomkassa
        if (Token.token != null) {
            return
        }
        val callGetTokenDto = CallGetTokenDto()
        callGetTokenDto.login = "sales@ecomkassa.ru"
        callGetTokenDto.pass = "ecomkassa"

        EcomNetworkService.getInstance()
            .getTokenPlaceHolderApi()
            .getToken(callGetTokenDto)
            .enqueue(object : Callback<TokenDto> {

                override fun onResponse(@NonNull call: Call<TokenDto>, @NonNull response: Response<TokenDto>) {
                    val tokenDto = response.body()
                    Log.i(MAIN_TAG, "onResponse for setUpPaymentSystem()")

                    if (tokenDto != null) {
                        Log.i(MAIN_TAG, "Token: ${tokenDto.token}")
                        Token.token = tokenDto.token
                        activity.onSetupEnded()
                    } else {
                        Log.e(MAIN_TAG, "tokenDto is null, error!")
                    }
                }

                override fun onFailure(@NonNull call: Call<TokenDto>, @NonNull t: Throwable) {
                    Log.i(MAIN_TAG, "ERROR: " + t.localizedMessage)
                    t.printStackTrace()
                }
            })
    }
}