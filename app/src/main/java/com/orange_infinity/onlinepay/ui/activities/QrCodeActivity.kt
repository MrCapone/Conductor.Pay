package com.orange_infinity.onlinepay.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.util.convertStringToQr
import kotlinx.android.synthetic.main.activity_qr_code.*

const val CHEQUE_LINK_KEY = "chequeLinkKey"

class QrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val chequeLink = intent.getStringExtra(CHEQUE_LINK_KEY)
        val payQrCodeBitmap = convertStringToQr(chequeLink, 500)
        imgQrExample.setImageBitmap(payQrCodeBitmap)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
