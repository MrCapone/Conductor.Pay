package com.orange_infinity.onlinepay.entities.dto

import java.text.SimpleDateFormat
import java.util.*

class SellDto {

    var external_id = UUID.randomUUID().toString()
    var receipt = Receipt()
    var timestamp: String

    init {
        val formatForDateNow = SimpleDateFormat("dd.MM.yy hh:mm:ss")
        val realTimestamp = Date()

        timestamp = formatForDateNow.format(realTimestamp)
    }
}

class Receipt {

    var client = Client()
    var company = Company()
    var items = mutableListOf<Item>()
    var payments = mutableListOf<Payments>()
    var vats = mutableListOf<Vats>()
    var total: Float = 10f

    init {
        items.add(Item())
        payments.add(Payments())
        vats.add(Vats())
    }
}

class Client {

    var email: String = "mothersasg@yandex.ru"
}

class Company {

    var email: String = "mothersasg@yandex.ru"
    var inn: String = "test_inn"
    var payment_address: String = "here"
}

class Item {

    var name: String = "Билет "
    var price: Float = 10f
    var quantity: Float = 1f
    var sum: Float = 10f
    var measurement_unit: String = "шт"
    var payment_method: String = "full_payment"
    var payment_object: String = "service"
    var vat = Vat()
}

class Vat {

    var type: String = "none"
}

class Payments {

    var type: Int = 1
    var sum: Float = 10f
}

class Vats {

    var type: String = "none"
    var sum: Float = 0f
}
