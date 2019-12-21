package com.orange_infinity.onlinepay.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.orange_infinity.onlinepay.entities.model.CashCheque

@Dao
interface CashChequeDao {

    @Insert(onConflict = REPLACE)
    fun insert(cashCheque: CashCheque)

    @Query("SELECT * FROM CashCheque WHERE externalId = :externalId LIMIT 1")
    fun findByExternalId(externalId: String): CashCheque?

    @Query("SELECT * FROM CashCheque")
    fun findAll(): List<CashCheque>

    @Query("SELECT * FROM CashCheque WHERE isSend = :isSend")
    fun findAllBySent(isSend: Boolean): List<CashCheque>

    @Query("SELECT * FROM CashCheque WHERE isSend = :isSend LIMIT 1")
    fun findOneChequeTicket(isSend: Boolean = false): CashCheque?
}