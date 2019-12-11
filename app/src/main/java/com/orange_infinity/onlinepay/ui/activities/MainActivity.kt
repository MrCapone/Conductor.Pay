package com.orange_infinity.onlinepay.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.ui.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

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
        //presenter.activity = this

        layoutInfo.setOnClickListener {
            val intent = Intent(this, SuccessPayedActivity::class.java)
            startActivity(intent)
        }

        addListeners()
    }

    private fun addListeners() {
        //Просто прибил гвоздями
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
}
