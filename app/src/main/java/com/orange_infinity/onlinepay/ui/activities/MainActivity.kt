package com.orange_infinity.onlinepay.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.os.StrictMode
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.ui.MainActivityInt
import com.orange_infinity.onlinepay.ui.dialogs.DownloadDialog
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

        // TODO("Просто прибиль гвоздями")
        btnLeft.setOnClickListener {
            val mainNum = Integer.parseInt(tvCost.text.toString().subSequence(0, 2).toString())
            val btnNum = Integer.parseInt(btnLeft.text.toString())
            btnLeft.text = mainNum.toString()
            if (btnNum != 21) {
                tvCost.text = "$btnNum рублей"
            } else {
                tvCost.text = "$btnNum рубль"
            }
        }

        btnRight.setOnClickListener {
            val mainNum = Integer.parseInt(tvCost.text.toString().subSequence(0, 2).toString())
            val btnNum = Integer.parseInt(btnRight.text.toString())
            btnRight.text = mainNum.toString()
            if (btnNum != 21) {
                tvCost.text = "$btnNum рублей"
            } else {
                tvCost.text = "$btnNum рубль"
            }
        }
    }

    override fun onCompleteDownload() { //TODO("Заменить на работу через FileProvider, удалить в MyApplication костыли")
        val intent = Intent(Intent.ACTION_VIEW)
        val apkSourceFile = File("${getExternalStorageDirectory()}/download/app.apk")

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

    private fun handleNetwork() {
        presenter.updateProgram()
        presenter.sendSignInInfoToServer()
    }
}
