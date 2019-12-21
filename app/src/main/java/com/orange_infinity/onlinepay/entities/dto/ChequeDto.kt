package com.orange_infinity.onlinepay.entities.dto

import com.orange_infinity.onlinepay.util.getPseudoId

class ChequeDto {

    lateinit var uuid: String
    lateinit var timestamp: String
    lateinit var status: String
    lateinit var permalink: String
    lateinit var kind: String
    var cost: Int = 0
    var deviceId = getPseudoId()
}