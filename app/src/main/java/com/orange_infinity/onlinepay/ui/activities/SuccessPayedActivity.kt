package com.orange_infinity.onlinepay.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.iposprinter.printerhelper.*
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.util.convertStringToQr
import com.orange_infinity.onlinepay.data.db.AppDatabase
import com.orange_infinity.onlinepay.useCase.CashChequeManager
import kotlinx.android.synthetic.main.activity_success_payed.*
import java.util.*


class SuccessPayedActivity : AppCompatActivity() {

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask
    private var link = ""

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
            //startActivityForResult(Intent(this, ScanningActivity::class.java), ScanningActivity.SCANNING_FOR_PRINTER)
            finish()
        }

        link = intent.getStringExtra(CHEQUE_LINK_KEY)
        timer = Timer()
        timerTask = FinishActivityTimerTask(this)
        timer.schedule(timerTask, 5000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK) {
//            //Printer is ready now
//            var printables = ArrayList<Printable>()
//            var printable = TextPrintable.Builder()
//                .setText("Hello World")
//                .build()
//            printables.add(printable)
//            Printooth.printer().print(printables)
//        }
    }

    private fun goToCheque() {
        val intent = Intent(this, QrCodeActivity::class.java)
        intent.putExtra(CHEQUE_LINK_KEY, link)
        startActivity(intent)
        finish()
    }

    private inner class FinishActivityTimerTask(val activity: Activity) : TimerTask() {
        override fun run() {
            val permalink = AppDatabase.getInstance(CashChequeManager.applicationContext).getCashChequeDao().findByExternalId(link)?.permalink
            if (!permalink.isNullOrBlank()) {
                link = permalink
            }
            activity.finish()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        printHelper.removeHelper(this)
    }
}
