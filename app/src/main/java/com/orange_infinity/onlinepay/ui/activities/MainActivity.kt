package com.orange_infinity.onlinepay.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.orange_infinity.onlinepay.R
import com.orange_infinity.onlinepay.daggerConfigurations.MyApplication
import com.orange_infinity.onlinepay.ui.activities.interfaces.IMainActivity
import com.orange_infinity.onlinepay.ui.dialogs.CardInfoDialog
import com.orange_infinity.onlinepay.ui.dialogs.CashPaymentSuccessDialog
import com.orange_infinity.onlinepay.ui.presenter.MainActivityCashPresenter
import com.orange_infinity.onlinepay.useCase.SUCCESS_PAYMENT_SOUND
import com.orange_infinity.onlinepay.useCase.SoundPlayer
import com.orange_infinity.onlinepay.util.formatCardNumber
import com.orange_infinity.onlinepay.util.getIntValue
import com.orange_infinity.onlinepay.util.getProgramVersion
import com.orange_infinity.onlinepay.util.getPseudoId
import io.github.tapcard.android.NFCCardReader
import io.github.tapcard.emvnfccard.model.EmvCard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tvAppVersion
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.IOException
import javax.inject.Inject
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import ru.yandex.money.android.sdk.*
import java.math.BigDecimal
import java.util.*
import ru.yandex.money.android.sdk.Checkout
import ru.yandex.money.android.sdk.MockConfiguration
import ru.yandex.money.android.sdk.TestParameters
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


private const val CASH_PAYMENT_TYPE = "Наличными"
private const val CARD_PAYMENT_TYPE = "Оплата картой"
private const val CARD_PAYMENT_DESCRIPTION = "Прислоните карту для оплаты проезда:"
private const val CASH_PAYMENT_DESCRIPTION = "НАЛИЧНЫЙ РАСЧЁТ"
private const val REQUEST_CODE_TOKENIZE = 0

class MainActivity : AppCompatActivity(), IMainActivity {

    @Inject
    lateinit var cashPresenter: MainActivityCashPresenter
    lateinit var soundPlayer: SoundPlayer

    //private lateinit var nfcCardReader: NFCCardReader

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

        soundPlayer = SoundPlayer.getInstance(this)
        cashPresenter.setUpPaymentSystem()

//        tvPaymentDescription.setOnClickListener {
//            cashPresenter.sendAllUnsentCheque()
//        }

        //nfcCardReader = NFCCardReader(this)
        changeCashToCard()
        tvAppVersion.text = "Версия приложения: ${getProgramVersion()}, deviceId: ${getPseudoId(this)}"
    }

    override fun onBackPressed() {
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
//        if (nfcCardReader.isSuitableIntent(intent)) {
//            readCardDataAsync(intent)
//        }
    }

    override fun onResume() {
        //nfcCardReader.enableDispatch()
        super.onResume()
    }

    override fun onPause() {
        //nfcCardReader.disableDispatch()
        super.onPause()
    }

//    @SuppressLint("StaticFieldLeak")
//    private fun readCardDataAsync(intent: Intent) {
//        object : AsyncTask<Intent, Any, EmvCard>() {intent
//
//            override fun doInBackground(vararg intents: Intent): EmvCard? {
//                try {
//                    return nfcCardReader.readCardBlocking(intents[0])
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    //throw RuntimeException("IOException!")
//                } catch (e: NFCCardReader.WrongIntentException) {
//                    e.printStackTrace()
//                    //throw RuntimeException("NFCCardReader.WrongIntentException!")
//                } catch (e: NFCCardReader.WrongTagTech) {
//                    e.printStackTrace()
//                    //throw RuntimeException("NFCCardReader.WrongTagTech!")
//                }
//
//                return null
//            }
//
//            override fun onPostExecute(emvCard: EmvCard) {
//                showCardInfo(emvCard)
//            }
//        }.execute(intent)
//    }

    private fun showCardInfo(card: EmvCard?) {
        val cardInfo: String

        cardInfo = if (card != null) {
            "Your card info:\n" +
                    "cardNumber: ${formatCardNumber(card.cardNumber, card.type)}\n" +
                    "expired date: ${card.expireDate}\n" +
                    "card holder: ${card.holderLastname} ${card.holderFirstname}"
        } else {
            "Error while reading card data :( \n Try again."
        }
        val dialog = CardInfoDialog.newInstance(cardInfo)
        dialog.show(supportFragmentManager, "")
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

    override fun onCashPayed(externalId: String) {
        //val dialog = CashPaymentSuccessDialog.newInstance()
        //dialog.show(supportFragmentManager, "")
        soundPlayer.standardPlay(SUCCESS_PAYMENT_SOUND)
        val intent = Intent(this, SuccessPayedActivity::class.java)
        intent.putExtra(CHEQUE_LINK_KEY, externalId)
        startActivity(intent)
    }


//    override fun onCashPayed(link: String) {
//        val intent = Intent(this, SuccessPayedActivity::class.java)
//        intent.putExtra(CHEQUE_LINK_KEY, link)
//        startActivity(intent)
//    }

    override fun onCardPayed(link: String) {
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
            if (btnNum != 22) {
                tvCost.text = "$btnNum рублей"
            } else {
                tvCost.text = "$btnNum рубля"
            }
        }

        btnRight.setOnClickListener {
            val mainNum = Integer.parseInt(tvCost.text.toString().subSequence(0, 2).toString())
            val btnNum = Integer.parseInt(btnRight.text.toString())
            btnRight.text = mainNum.toString()
            if (btnNum != 22) {
                tvCost.text = "$btnNum рублей"
            } else {
                tvCost.text = "$btnNum рубля"
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
            if (btnPaymentType.text == CARD_PAYMENT_TYPE) {
                cashPresenter.payByCash(tvCost.text.toString().getIntValue())
            } else {
                val mainNum = Integer.parseInt(tvCost.text.toString().subSequence(0, 2).toString())
                val paymentParameters = PaymentParameters(
                    Amount(BigDecimal.valueOf(mainNum.toDouble()), Currency.getInstance("RUB")),
                    "Билет",
                    "Описание билета",
                    "test_NjYxNjkwz4AWvgemOP9PTIAzalQMczCDg8x33xlYwhg",
                    "661690",
                    SavePaymentMethod.ON,
                    paymentMethodTypes = setOf(PaymentMethodType.BANK_CARD)
                )
                val testParameters = TestParameters(
                    showLogs = true,
                    googlePayTestEnvironment = true,
                    mockConfiguration = MockConfiguration(
                        completeWithError = false,
                        paymentAuthPassed = true,
                        linkedCardsCount = 5
                    )
                )
                val intent = Checkout.createTokenizeIntent(this, paymentParameters, testParameters)
                startActivityForResult(intent, REQUEST_CODE_TOKENIZE)
//                val intent = Checkout.createTokenizeIntent(this, paymentParameters)
//                startActivityForResult(intent, REQUEST_CODE_TOKENIZE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TOKENIZE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val result = Checkout.createTokenizationResult(data!!)
                }
                Activity.RESULT_CANCELED -> {

                }
            }
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
        //nfcCardReader.enableDispatch()
    }

    private fun changeCardToCash() {
        //btnPayByCash.visibility = View.GONE
        btnPaymentType.text = CASH_PAYMENT_TYPE
        btnPaymentType.textSize = 20f

        imgNfc.visibility = View.VISIBLE
        tvPaymentDescription.text = CARD_PAYMENT_DESCRIPTION
        tvPaymentDescription.textSize = 18f
        tvPaymentDescription.gravity = Gravity.START
        //nfcCardReader.disableDispatch()
    }
}
