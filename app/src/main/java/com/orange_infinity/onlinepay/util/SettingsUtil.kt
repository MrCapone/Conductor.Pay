package com.orange_infinity.onlinepay.util

import android.os.Environment
import com.orange_infinity.onlinepay.BuildConfig

const val MAIN_TAG = "MAIN_TAG"

fun getProgramVersion(): Int {
    return BuildConfig.VERSION_CODE
}

fun getGalleryPath(): String {
    return "${Environment.getExternalStorageDirectory()}/"
}