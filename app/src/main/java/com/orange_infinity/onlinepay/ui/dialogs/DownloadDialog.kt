package com.orange_infinity.onlinepay.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.orange_infinity.onlinepay.R

class DownloadDialog : DialogFragment() {

    companion object {
        fun newInstance() = DownloadDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.download_dialog, null)


        return AlertDialog.Builder(activity)
            .setView(v)
            .setCancelable(false)
            .create()
    }
}