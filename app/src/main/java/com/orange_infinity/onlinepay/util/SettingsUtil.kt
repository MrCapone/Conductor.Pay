package com.orange_infinity.onlinepay.util

import com.orange_infinity.onlinepay.BuildConfig

const val MAIN_TAG = "MAIN_TAG"

fun getProgramVersion(): Int {
    return BuildConfig.VERSION_CODE
}