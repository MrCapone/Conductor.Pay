package com.orange_infinity.onlinepay.ui

import android.content.Context

interface DownloaderActivity {

    fun onCompleteDownload()

    fun onStartDownload()

    fun onErrorDownload()

    fun processCurrentVerIsLast()

    fun getAppContext(): Context
}