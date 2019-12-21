package com.orange_infinity.onlinepay.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.orange_infinity.onlinepay.R

class CashPaymentSuccessDialog : DialogFragment() {

    companion object {
        fun newInstance() = CashPaymentSuccessDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_cash_success_payment, null)


        return AlertDialog.Builder(activity)
            .setView(v)
            .create()
    }
}