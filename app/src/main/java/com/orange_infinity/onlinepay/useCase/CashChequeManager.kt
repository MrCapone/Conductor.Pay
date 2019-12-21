package com.orange_infinity.onlinepay.useCase

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.orange_infinity.onlinepay.data.db.AppDatabase
import com.orange_infinity.onlinepay.entities.dto.ChequeDto
import com.orange_infinity.onlinepay.entities.model.CashCheque

private const val CASH_CHEQUE_MANAGER_TAG = "CashChequeManagerTag"

class CashChequeManager {

    companion object {
        lateinit var applicationContext: Context
        lateinit var externalId: String
        var ticketCost: Int = 0
    }

    private lateinit var chequeSaver: ChequeSaver
    private lateinit var cashTickerHandler: CashTickerHandler

    fun saveChequeByExternalId(appContext: Context, externalId: String, cost: Int) {
        chequeSaver = ChequeSaver()
        applicationContext = appContext
        ticketCost = cost

        chequeSaver.execute(externalId)
    }

    fun processCashTicket(appContext: Context, cheque: ChequeDto, extId: String) {
        externalId = extId
        cashTickerHandler = CashTickerHandler()
        applicationContext = appContext
        cashTickerHandler.execute(cheque)
    }

    class ChequeSaver : AsyncTask<String, Unit, Unit>() {

        override fun doInBackground(vararg params: String) {
            val externalId = params[0]
            val cashCheque = CashCheque()
            cashCheque.externalId = externalId
            cashCheque.cost = ticketCost

            Log.i(CASH_CHEQUE_MANAGER_TAG, "Start to save new cashCheque, externalId = $externalId")
            AppDatabase.getInstance(applicationContext).getCashChequeDao().insert(cashCheque)
            Log.i(CASH_CHEQUE_MANAGER_TAG, "Success saving not full cashCheque with externalId = $externalId")
        }
    }

    class CashTickerHandler : AsyncTask<ChequeDto, Unit, Unit>() {

        override fun doInBackground(vararg params: ChequeDto) {
            val chequeDto = params[0]
            val cashCheque = AppDatabase.getInstance(applicationContext).getCashChequeDao().findByExternalId(externalId)

            if (cashCheque != null) {
                cashCheque.timestamp = chequeDto.timestamp
                cashCheque.permalink = chequeDto.permalink
                cashCheque.isSend = true

                Log.i(CASH_CHEQUE_MANAGER_TAG, "Start to save new cashCheque, externalId = $externalId")
                AppDatabase.getInstance(applicationContext).getCashChequeDao().insert(cashCheque)
                Log.i(CASH_CHEQUE_MANAGER_TAG, "Success saving full cashCheque, externalId = $externalId")
            } else {
                Log.i(CASH_CHEQUE_MANAGER_TAG, "Can not find cashCheque with this externalId = $externalId")
            }
        }
    }
}
