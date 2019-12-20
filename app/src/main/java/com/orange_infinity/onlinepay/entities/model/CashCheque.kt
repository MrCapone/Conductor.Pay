package com.orange_infinity.onlinepay.entities.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CashCheque {

    @PrimaryKey
    lateinit var externalId: String
    var timestamp: String? = null
    var permalink: String? = null
    var isSend: Boolean = false
}