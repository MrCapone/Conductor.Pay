package com.orange_infinity.onlinepay.ui

import android.content.Context

interface MainActivityInt {

    fun onCompleteDownload()

    fun onStartDownload()

    fun onErrorDownload()

    fun processCurrentVerIsLast()

    fun getAppContext(): Context
}