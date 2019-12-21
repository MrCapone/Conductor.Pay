package com.orange_infinity.onlinepay.ui.presenter

import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.orange_infinity.onlinepay.ui.activities.interfaces.DownloaderActivity
import com.orange_infinity.onlinepay.useCase.ServerEntryController
import com.orange_infinity.onlinepay.util.MAIN_TAG
import com.orange_infinity.onlinepay.util.getPseudoId
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class RegistrationPresenter(
    val serverEntryController: ServerEntryController
) : AsyncTask<String, Void, Boolean>(), UpdateLoader {

    lateinit var activity: DownloaderActivity

    override fun processCurrentVerIsLast() {
        activity.processCurrentVerIsLast()
    }

    override fun update(url: String) {
        execute(url)
    }

    override fun onPreExecute() {
        activity.onStartDownload()
    }

    override fun doInBackground(vararg params: String?): Boolean {
        return try {
            val url = params[0] ?: return false
            loadApk(url)
            true
        } catch (ex: Error) {
            Log.i(MAIN_TAG, "Error download new version, exception: $ex")
            activity.onErrorDownload()
            false
        }
    }

    override fun onPostExecute(result: Boolean) {
        if (result) {
            activity.onCompleteDownload()
        }
    }

    private fun loadApk(apkUrl: String) {
        try {
            val url = URL(apkUrl)
            val c = url.openConnection() as HttpURLConnection
            c.requestMethod = "GET"
            c.doOutput = true
            c.connect()

            val filePath = "${Environment.getExternalStorageDirectory()}/download/"
            val file = File(filePath)
            file.mkdirs()
            val outputFile = File(file, "app.apk")
            val fos = FileOutputStream(outputFile)

            val inputStream = c.inputStream

            val buffer = ByteArray(1024)
            var len1 = inputStream.read(buffer)
            while (len1 != -1) {
                fos.write(buffer, 0, len1)
                len1 = inputStream.read(buffer)
            }
            fos.close()
            inputStream.close()//till here, it works fine - .apk is download to my sdcard in download file

            activity.onCompleteDownload()
        } catch (e: IOException) {
            Toast.makeText(activity.getAppContext(), "Update error!", Toast.LENGTH_LONG).show()
            activity.onErrorDownload()
        }
    }

    fun updateProgram() {
        serverEntryController.isNeedUpdateProgram(this)
    }

    // TODO("Change pseudoId to IMEI")
    fun sendSignInInfoToServer() {
        val pseudoID = getPseudoId()
        Log.i(MAIN_TAG, "pseudoId: $pseudoID")

        if (serverEntryController.signIn(pseudoID)) {
            serverEntryController.sighUp(pseudoID)
        }
    }
}