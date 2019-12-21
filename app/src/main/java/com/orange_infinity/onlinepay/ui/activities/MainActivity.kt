package com.orange_infinity.onlinepay.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.ui.activities.interfaces.IMainActivity
import com.orange_infinity.onlinepay.ui.presenter.MainActivityCashPresenter
import com.orange_infinity.onlinepay.util.getIntValue
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val CASH_PAYMENT_TYPE = "Наличными"
private const val CARD_PAYMENT_TYPE = "Оплата картой"
private const val CARD_PAYMENT_DESCRIPTION = "Прислоните карту или телефон для оплаты проезда:"
private const val CASH_PAYMENT_DESCRIPTION = "НАЛИЧНЫЙ РАСЧЁТ"

class MainActivity : AppCompatActivity(), IMainActivity {

    @Inject
    lateinit var cashPresenter: MainActivityCashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        (application as MyApplication).appComponent.inject(this)
        MainActivityCashPresenter.activity = this

        btnPaymentType.text = CASH_PAYMENT_TYPE
        addListeners()
        blockViews()

        cashPresenter.setUpPaymentSystem()

//        tvPaymentDescription.setOnClickListener {
//            cashPresenter.sendAllUnsentCheque()
//        }
    }

    private fun blockViews() {
        setEnableToAllButtons(false)
    }

    override fun getAppContext(): Context {
        return applicationContext
    }

    override fun onSetupEnded() {
        setEnableToAllButtons(true)
    }

    override fun onCashPayed() {
        // DO SMTH
        cashPresenter.playPaymentMelody()
    }


//    override fun onCashPayed(link: String) {
//        val intent = Intent(this, SuccessPayedActivity::class.java)
//        intent.putExtra(CHEQUE_LINK_KEY, link)
//        startActivity(intent)
//    }

    override fun onCardPayed(link: String) {
        cashPresenter.playPaymentMelody()
    }

    private fun setEnableToAllButtons(isEnable: Boolean) {
        btnLeft.isEnabled = isEnable
        btnRight.isEnabled = isEnable
        btnPaymentType.isEnabled = isEnable
        btnPayByCash.isEnabled = isEnable
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

        btnPaymentType.setOnClickListener {
            if (btnPaymentType.text == CASH_PAYMENT_TYPE) {
                changeCashToCard()
            } else {
                changeCardToCash()
            }
        }

        btnPayByCash.setOnClickListener {
            cashPresenter.payByCash(tvCost.text.toString().getIntValue())
        }
    }

    private fun changeCashToCard() {
        btnPayByCash.visibility = View.VISIBLE
        btnPaymentType.text = CARD_PAYMENT_TYPE
        btnPaymentType.textSize = 18f

        imgNfc.visibility = View.GONE
        tvPaymentDescription.text = CASH_PAYMENT_DESCRIPTION
        tvPaymentDescription.textSize = 24f
        tvPaymentDescription.gravity = Gravity.CENTER
    }

    private fun changeCardToCash() {
        btnPayByCash.visibility = View.GONE
        btnPaymentType.text = CASH_PAYMENT_TYPE
        btnPaymentType.textSize = 20f

        imgNfc.visibility = View.VISIBLE
        tvPaymentDescription.text = CARD_PAYMENT_DESCRIPTION
        tvPaymentDescription.textSize = 18f
        tvPaymentDescription.gravity = Gravity.START
    }
}
