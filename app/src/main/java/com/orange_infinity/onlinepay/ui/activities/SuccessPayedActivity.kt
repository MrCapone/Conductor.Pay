package com.orange_infinity.onlinepay.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.orange_infinity.onlinepay.R
import com.iposprinter.printerhelper.*
import com.orange_infinity.onlinepay.util.convertStringToQr
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

        printHelper = PrintHelper().createHelper(this)

        btnBack.setOnClickListener {
            finish()
        }

        tvQrCode.setOnClickListener {
            goToCheque()
        }

        tcCheque.setOnClickListener {
            val number = 5
            val time = printHelper.getCurrentTimeStamp(this)
            val turn = 1
            val OFD = arrayOf("_____", "_____", "_____", "_____", "_____")
            val QR = convertStringToQr("http://wwww.nalog.ru", 500)
            printHelper.printReceipt(number, time, turn, OFD, QR);
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
            activity.finish()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        printHelper.removeHelper(this)
    }
}
