package com.orange_infinity.onlinepay.entities.dto

class ChequeDto {

    lateinit var uuid: String
    lateinit var timestamp: String
    lateinit var status: String
    lateinit var permalink: String
    lateinit var kind: String
    lateinit var deviceId: String
    var cost: Int = 0
}