package com.orange_infinity.onlinepay.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings.Secure
import java.util.regex.Pattern


fun String.getIntValue(): Int {
    val matcher = Pattern.compile("\\d+").matcher(this)
    matcher.find()
    return Integer.valueOf(matcher.group())
}

@SuppressLint("MissingPermission")
fun getPseudoId(context: Context): String {
    try {
//        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
//        val device_id = tm!!.deviceId
//        return device_id

        val androidID = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
        return androidID
    } catch (ex: Exception) {
        return "35${Build.BOARD.length % 10}${Build.BRAND.length % 10}" +
                "${Build.SUPPORTED_ABIS.size % 10}${Build.DEVICE.length % 10}" +
                "${Build.DISPLAY.length % 10}${Build.HOST.length % 10}" +
                "${Build.ID.length % 10}${Build.MANUFACTURER.length % 10}" +
                "${Build.MODEL.length % 10}${Build.PRODUCT.length % 10}" +
                "${Build.TAGS.length % 10}${Build.TYPE.length % 10}" +
                "${Build.USER.length % 10}"
    }
}