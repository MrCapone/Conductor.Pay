package com.orange_infinity.onlinepay.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.orange_infinity.onlinepay.R
import kotlinx.android.synthetic.main.dialog_card_info.*
import java.util.concurrent.TimeUnit

class CardInfoDialog : DialogFragment() {

    private lateinit var cardInfo: String

    companion object {
        fun newInstance(cardInfo: String): CardInfoDialog {
            val dialog = CardInfoDialog()
            dialog.cardInfo = cardInfo

            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_card_info, null)

        //tvCardInfo.text = cardInfo
//        tvCardInfo.text = "Установка связи..."
//        fakeReading()

        return AlertDialog.Builder(activity)
            .setView(v)
            .create()
    }

    @SuppressLint("StaticFieldLeak")
    private fun fakeReading() {
        object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg params: Unit) {
                TimeUnit.MILLISECONDS.sleep(2000)
            }

            override fun onPostExecute(result: Unit?) {
                tvCardInfo.text = "Нет связи с оператором"
            }
        }
    }
}