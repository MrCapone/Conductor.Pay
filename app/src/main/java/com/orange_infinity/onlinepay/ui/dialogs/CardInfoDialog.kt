package com.orange_infinity.onlinepay.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.orange_infinity.onlinepay.R
import kotlinx.android.synthetic.main.dialog_card_info.*

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

        tvCardInfo.text = cardInfo

        return AlertDialog.Builder(activity)
            .setView(v)
            .create()
    }
}