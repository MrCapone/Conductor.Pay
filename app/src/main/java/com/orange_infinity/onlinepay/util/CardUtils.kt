package com.orange_infinity.onlinepay.util

import android.text.TextUtils
import io.github.tapcard.emvnfccard.enums.EmvCardScheme

/**
 * Method used to format card number
 *
 * @param pCardNumber card number to display
 * @param pType       card type
 * @return the card number formated
 */
fun formatCardNumber(pCardNumber: String, pType: EmvCardScheme?): String {
    return if (!TextUtils.isEmpty(pCardNumber)) {
        // format amex
        if (pType != null && pType == EmvCardScheme.AMERICAN_EXPRESS) {
            deleteWhitespace(pCardNumber).replaceFirst("\\d{4}".toRegex(), "$0 ")
                .replaceFirst("\\d{6}".toRegex(), "$0 ")
                .replaceFirst("\\d{5}".toRegex(), "$0").trim { it <= ' ' }
        } else {
            deleteWhitespace(pCardNumber).replace("\\d{4}".toRegex(), "$0 ").trim({ it <= ' ' })
        }
    } else {
        "0000 0000 0000 0000"
    }
}

/**
 *
 * Deletes all whitespaces from a String as defined by
 * [Character.isWhitespace].
 *
 *
 * <pre>
 * StringUtils.deleteWhitespace(null)         = null
 * StringUtils.deleteWhitespace("")           = ""
 * StringUtils.deleteWhitespace("abc")        = "abc"
 * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
</pre> *
 *
 * @param str the String to delete whitespace from, may be null
 * @return the String without whitespaces, `null` if null String input
 */
private fun deleteWhitespace(str: String): String {
    if (TextUtils.isEmpty(str)) {
        return str
    }
    val sz = str.length
    val chs = CharArray(sz)
    var count = 0
    for (i in 0 until sz) {
        if (!Character.isWhitespace(str[i])) {
            chs[count++] = str[i]
        }
    }
    return if (count == sz) {
        str
    } else String(chs, 0, count)
}