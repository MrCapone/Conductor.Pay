package com.orange_infinity.onlinepay.ui.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orange_infinity.onlinepay.R
import ru.yandex.money.android.sdk.Checkout

class CardReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_reader)

        //val result = Checkout.createScanBankCardIntent(cardNumber, expirationMonth, expirationYear)
        val result = Checkout.createScanBankCardIntent("5555555555554444", 10, 22)
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}
