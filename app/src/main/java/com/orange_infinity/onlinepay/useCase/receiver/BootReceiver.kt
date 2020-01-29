package com.orange_infinity.onlinepay.useCase.receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.orange_infinity.onlinepay.ui.activities.MainActivity

private const val BOOT_ACTION = "android.intent.action.BOOT_COMPLETED"
private const val BOOT_ACTION2 = "android.intent.action.QUICKBOOT_POWERON"
private const val BOOT_ACTION3 = "com.htc.intent.action.QUICKBOOT_POWERON"

class BootReceiver : BroadcastReceiver() {

    private lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        val action = intent.action

        Toast.makeText(context.applicationContext, "Receiver is work!", Toast.LENGTH_LONG).show()
        val activityIntent = Intent(context, MainActivity::class.java)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(activityIntent)

//        if (action.equals(BOOT_ACTION, ignoreCase = true) || action.equals(BOOT_ACTION2, ignoreCase = true)
//            || action.equals(BOOT_ACTION3, ignoreCase = true)) {
//
//            if (hasPermissions(context)) {
//                val activityIntent = Intent(context, MainActivity::class.java)
//                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(activityIntent)
//            }
//        } else {
//            val activityIntent = Intent(context, MainActivity::class.java)
//            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(activityIntent)
//        }
    }

    private fun hasPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}