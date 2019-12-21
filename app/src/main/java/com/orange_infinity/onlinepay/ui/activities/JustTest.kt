//package com.orange_infinity.onlinepay.ui.activities
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.AsyncTask
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.text.TextUtils
//import android.text.format.DateFormat
//import android.view.WindowManager
//import android.widget.TextView
//import com.orange_infinity.onlinepay.R
//import com.orange_infinity.onlinepay.util.formatCardNumber
//import io.github.tapcard.android.NFCCardReader
//import io.github.tapcard.emvnfccard.model.EmvCard
//import kotlinx.android.synthetic.main.activity_test_nfc.*
//import java.io.IOException
//import java.lang.RuntimeException
//
//class TestNfcActivity : AppCompatActivity() {
//
//    private lateinit var nfcCardReader: NFCCardReader
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_nfc)
//
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//
//        nfcCardReader = NFCCardReader(this)
//
//        btnStartNfc.setOnClickListener {
//        }
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        if (nfcCardReader.isSuitableIntent(intent)) {
//            textView.text = "Reading…"
//            readCardDataAsync(intent)
//        }
//    }
//
//    override fun onResume() {
//        nfcCardReader.enableDispatch()
//        super.onResume()
//    }
//
//    override fun onPause() {
//        nfcCardReader.disableDispatch()
//        super.onPause()
//    }
//
//    private fun showCardInfo(card: EmvCard?) {
//        val text: String
//
//        if (card != null) {
//            text = "Your card info: "
////            text = TextUtils.join(
////                "\n",
////                arrayOf(
////                    formatCardNumber(card.cardNumber, card.type),
////                    DateFormat.format("M/y", card.expireDate),
////                    "---",
////                    "Bank info (probably): ",
////                    card.atrDescription,
////                    "---",
////                    card.toString().replace(", ", ",\n")
////                )
////            )
//        } else {
//            text = "Error while reading card data :( \\nTry again: tap card and hold for a while."
//        }
//        textView.text = text
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private fun readCardDataAsync(intent: Intent) {
//        object : AsyncTask<Intent, Any, EmvCard>() {
//
//            override fun doInBackground(vararg intents: Intent): EmvCard? {
//                try {
//                    return nfcCardReader.readCardBlocking(intents[0])
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    throw RuntimeException("IOException!")
//                } catch (e: NFCCardReader.WrongIntentException) {
//                    e.printStackTrace()
//                    throw RuntimeException("NFCCardReader.WrongIntentException!")
//                } catch (e: NFCCardReader.WrongTagTech) {
//                    e.printStackTrace()
//                    throw RuntimeException("NFCCardReader.WrongTagTech!")
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
//}
