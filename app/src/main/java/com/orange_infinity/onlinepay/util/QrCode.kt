package com.orange_infinity.onlinepay.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.util.TypedValue
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import kotlin.math.roundToInt


fun convertStringToQr(text: String, width: Int): Bitmap {
    val qrgEncoder = QRGEncoder(text, null, QRGContents.Type.TEXT, width)
    return qrgEncoder.encodeAsBitmap()
}

fun convertDpToPx(dp: Int, resources: Resources): Int {
    val dm = resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), dm).toInt()
}

fun convertPxToDp(px: Int, resources: Resources): Int {
    val displayMetrics = resources.displayMetrics
    return (px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}