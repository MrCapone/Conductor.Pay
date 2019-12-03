package com.orange_infinity.onlinepay.ui.presenter

import android.os.AsyncTask
import android.os.Build
import android.util.Log
import com.orange_infinity.onlinepay.ui.MainActivityInt
import com.orange_infinity.onlinepay.useCase.TicketManager
import com.orange_infinity.onlinepay.useCase.ServerEntryController
import com.orange_infinity.onlinepay.util.MAIN_TAG
import java.io.IOException
import java.net.URL
import android.widget.Toast
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection


class MainActivityPresenter(
    val ticketManager: TicketManager,
    val serverEntryController: ServerEntryController
) : AsyncTask<String, Void, Boolean>(), UpdateLoader {

    lateinit var activity: MainActivityInt

    // TODO("Change pseudoId to IMEI")
    fun sendSignInInfoToServer() {
        val pseudoID = "35${Build.BOARD.length % 10}${Build.BRAND.length % 10}" +
                "${Build.SUPPORTED_ABIS.size % 10}${Build.DEVICE.length % 10}" +
                "${Build.DISPLAY.length % 10}${Build.HOST.length % 10}" +
                "${Build.ID.length % 10}${Build.MANUFACTURER.length % 10}" +
                "${Build.MODEL.length % 10}${Build.PRODUCT.length % 10}" +
                "${Build.TAGS.length % 10}${Build.TYPE.length % 10}" +
                "${Build.USER.length % 10}"
        Log.i(MAIN_TAG, "pseudoId: $pseudoID")

        if (serverEntryController.signIn(pseudoID)) {
            serverEntryController.sighUp(pseudoID)
        }
    }

    fun updateProgram() {
        serverEntryController.isNeedUpdateProgram(this)
    }

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
            //serverEntryController.downloadNewVersion()
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
            //activity.onCompleteDownload()
        }
        activity.processCurrentVerIsLast()
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

//    @Throws(IOException::class)
//    fun download(url: URL): ByteArray {
//        val uc = url.openConnection()
//        val len = uc.contentLength
//        val `is` = BufferedInputStream(uc.getInputStream())
//        try {
//            val data = ByteArray(len)
//            var offset = 0
//            while (offset < len) {
//                val read = `is`.read(data, offset, data.size - offset)
//                if (read < 0) {
//                    break
//                }
//                offset += read
//            }
//            if (offset < len) {
//                throw IOException(
//                    String.format("Read %d bytes; expected %d", offset, len)
//                )
//            }
//            return data
//        } finally {
//            `is`.close()
//        }
//    }
}