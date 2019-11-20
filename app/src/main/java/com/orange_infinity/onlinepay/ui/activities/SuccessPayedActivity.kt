package com.orange_infinity.onlinepay.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orange_infinity.onlinepay.R
import kotlinx.android.synthetic.main.activity_success_payed.*

class SuccessPayedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_payed)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
