package com.orange_infinity.onlinepay.util

import android.graphics.Bitmap
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder

fun convertStringToQr(text: String, width: Int): Bitmap {
    val qrgEncoder = QRGEncoder(text, null, QRGContents.Type.TEXT, width)
    return qrgEncoder.encodeAsBitmap()
}