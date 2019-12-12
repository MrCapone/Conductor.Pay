package com.orange_infinity.onlinepay.util

import java.util.regex.Pattern

fun String.getIntValue(): Int {
    val matcher = Pattern.compile("\\d+").matcher(this)
    matcher.find()
    return Integer.valueOf(matcher.group())
}