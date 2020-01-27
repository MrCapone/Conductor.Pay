package com.orange_infinity.onlinepay.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.util.PrintHelper
import kotlinx.android.synthetic.main.activity_success_payed.*
import java.util.*

class SuccessPayedActivity : AppCompatActivity() {

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask

    private lateinit var printHelper: PrintHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_payed)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        printHelper = PrintHelper(this)

        btnBack.setOnClickListener {
            finish()
        }

        tvQrCode.setOnClickListener {
            goToCheque()
        }

        tcCheque.setOnClickListener {
            printHelper.printText()
        }

        timer = Timer()
        timerTask = FinishActivityTimerTask(this)
        timer.schedule(timerTask, 5000)
    }

    private fun goToCheque() {
        val chequeLink = intent.getStringExtra(CHEQUE_LINK_KEY)
        val intent = Intent(this, QrCodeActivity::class.java)
        intent.putExtra(CHEQUE_LINK_KEY, chequeLink)
        startActivity(intent)
        finish()
    }

    private inner class FinishActivityTimerTask(val activity: Activity) : TimerTask() {

        override fun run() {
            printHelper.removeHelper(activity)
            activity.finish()
        }
    }
}
