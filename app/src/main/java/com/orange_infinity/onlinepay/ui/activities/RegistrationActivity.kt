package com.orange_infinity.onlinepay.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.ui.DownloaderActivity
import com.orange_infinity.onlinepay.ui.dialogs.DownloadDialog
import com.orange_infinity.onlinepay.ui.presenter.RegistrationPresenter
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.File
import javax.inject.Inject

private const val REQUEST_READ_STORAGE = 113

class RegistrationActivity : AppCompatActivity(), DownloaderActivity {

    @Inject
    lateinit var presenter: RegistrationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        (application as MyApplication).appComponent.inject(this)
        presenter.activity = this

        requestPermission(this)
        //handleNetwork()

        btnEnter.setOnClickListener {
            if (hasPermissions(this)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun handleNetwork() {
        presenter.updateProgram()
        presenter.sendSignInInfoToServer()
    }

    private fun requestPermission(context: Activity) { //TODO("Если отказано в доступе, запросить снова!")
        if (!hasPermissions(context)) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_STORAGE
            )
        } else {
            handleNetwork()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_STORAGE) {
            if (hasPermissions(this)) {
                handleNetwork()
            } else {
                requestPermission(this)
            }
        }
    }

    override fun onCompleteDownload() {
        val intent = Intent(Intent.ACTION_VIEW)
        val apkSourceFile = File("${Environment.getExternalStorageDirectory()}/download/app.apk")

        intent.setDataAndType(
            Uri.fromFile(apkSourceFile),
            //FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", apkSourceFile),
            "application/vnd.android.package-archive"
        )
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onStartDownload() {
        val dialog = DownloadDialog.newInstance()
        dialog.show(supportFragmentManager, "")
    }

    override fun onErrorDownload() {
        Toast.makeText(this, "Ошибка загрузки обновления ;(", Toast.LENGTH_SHORT).show()
    }

    override fun processCurrentVerIsLast() {
        Toast.makeText(this, "Запущена последняя версия", Toast.LENGTH_SHORT).show()
    }

    override fun getAppContext(): Context {
        return applicationContext
    }

    private fun hasPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}
