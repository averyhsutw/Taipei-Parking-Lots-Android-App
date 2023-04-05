package com.example.taipeiparkinglots.ext

import android.text.SpannableString
import android.text.style.UnderlineSpan

object UnderlineSpannable {
    fun getUnderlineSpannable(str: String): SpannableString {
        val spannable = SpannableString(str)
        spannable.setSpan(UnderlineSpan(), 0, spannable.length, 0)
        return spannable
    }
}