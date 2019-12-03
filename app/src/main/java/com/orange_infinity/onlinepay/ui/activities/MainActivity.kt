package com.orange_infinity.onlinepay.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.ui.MainActivityInt
import com.orange_infinity.onlinepay.ui.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityInt {

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        (application as MyApplication).appComponent.inject(this)
        presenter.activity = this
        handleNetwork()

        layoutInfo.setOnClickListener {
            val intent = Intent(this, SuccessPayedActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCompleteDownload() {
        val intent = Intent(Intent.ACTION_VIEW)
        val file = File("${getExternalStorageDirectory()}/download/app.apk")

        intent.setDataAndType(
            Uri.fromFile(file),
            "application/vnd.android.package-archive"
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onStartDownload() {
        // Отобразить начало загрузки
    }

    override fun onErrorDownload() {
        // Загрузка свалилась с ошибкой
    }

    override fun processCurrentVerIsLast() {
    }

    override fun getAppContext(): Context {
        return applicationContext
    }

    private fun handleNetwork() {
        presenter.updateProgram()
        presenter.sendSignInInfoToServer()
    }
}
