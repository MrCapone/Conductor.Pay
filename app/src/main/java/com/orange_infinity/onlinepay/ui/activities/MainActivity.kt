package com.orange_infinity.onlinepay.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
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
        presenter.updateProgram()
        presenter.sendSignInInfoToServer()

//        val intent = Intent(this, SuccessPayedActivity::class.java)
//        startActivity(intent)

        layoutInfo.setOnClickListener {
            val intent = Intent(this, SuccessPayedActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onBackPressed() {
//    }
}
