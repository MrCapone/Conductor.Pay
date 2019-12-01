package com.orange_infinity.onlinepay.ui.activities

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.TagTechnology
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.orange_infinity.onlinepay.R
import io.github.tapcard.android.NFCCardReader
import io.github.tapcard.emvnfccard.model.EmvCard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    private var nfcCardReader: NFCCardReader? = null
    private var cardReadDisposable: Disposable? = null
    private var isOnCreateErr = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        try {
            nfcCardReader = NFCCardReader(this)
            //nfcCardReader!!.enableDispatch()
//
//
//            if (nfcCardReader!!.isSuitableIntent(intent)) {
//                tvTextParsing.text = "Reading..."
//
//                cardReadDisposable?.dispose()
//                cardReadDisposable = nfcCardReader!!
//                    .readCardRx2(intent)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        { emvCard -> fillCard(emvCard) },
//                        { throwable -> showError(throwable) })
//
//            } else {
//                tvTextParsing.text = "This devise is do not support nfc"
//            }

        } catch (ex: Exception) {
            tvTextParsing.text = "Exception in onCreate(): ${ex.message}"
            isOnCreateErr = true
        }

//        val techList = arrayListOf(3)
//        val tag = Tag(1, techList)
//        intent.putExtra(NfcAdapter.EXTRA_TAG, )

        btnTest.setOnClickListener {
            //doIt(intent)
        }

        onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        nfcCardReader!!.enableDispatch()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        try {
            if (nfcCardReader!!.isSuitableIntent(intent)) {
                Toast.makeText(this, "Start to reading card", Toast.LENGTH_LONG).show()
                tvTextParsing.text = "Reading..."

                cardReadDisposable?.dispose()
                cardReadDisposable = nfcCardReader!!
                    .readCardRx2(intent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { emvCard -> fillCard(emvCard) },
                        { throwable -> showError(throwable) })
            } else {
                tvTextParsing.text = "This devise is do not support nfc"
            }
        } catch (ex: Exception) {
            tvTextParsing.text = "Exception in onNewIntent(): ${ex.message}"
        } finally {
            if (isOnCreateErr) {
                tvTextParsing.append("\n And error in onCreate()")
            }
        }
    }

    private fun fillCard(card: EmvCard) {
        tvTextParsing.text = "Card number: ${card.cardNumber} \n Card holder: ${card.holderLastname} " +
                "${card.holderFirstname} \n card expire date: ${card.expireDate}"
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_LONG).show()
    }
}
