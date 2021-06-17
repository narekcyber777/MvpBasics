package com.narcyber.mvpbasics.utils

import android.graphics.Color
import android.graphics.Typeface
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView

class TextManipulator(val view: TextView) {
    var span: Spannable = SpannableStringBuilder("")
    var tCall: TextManipulatorCallBack? = null
    var recycleBin: MutableSet<ClickableSpan> = hashSetOf()

    init {
        view.movementMethod = LinkMovementMethod.getInstance()
    }

    fun addText(text: String = "") {
        span = SpannableString(text)
    }

    fun with(callBack: TextManipulatorCallBack) {
        this.tCall = callBack
    }

    fun push() {
        if (view.text.toString().isEmpty()) {
            view.text = span
            return
        }
        view.append(span)
    }

    fun recycle() {
        recycleBin.clear()
    }

    fun makeClickable(event: String = "", params: Params) {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                tCall?.onEvent(event)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.bgColor = params.colorBg
                ds.color = params.colorText
                ds.isUnderlineText = params.isUnderlined
                ds.isAntiAlias = true
                view.invalidate()
            }
        }
        recycleBin.add(clickableSpan)
        val normalSpan = StyleSpan(params.styleText.style)
        clickableSpan.updateDrawState(view.paint)

        span.setSpan(normalSpan, 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(clickableSpan, 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    }

    fun addSpace(length: Int) {
        val stringBuilder = StringBuilder(" ")
        for (i in 0 until length) {
            stringBuilder.append(" ")
        }
        span = SpannableString(stringBuilder.toString())
    }

    interface TextManipulatorCallBack {
        fun onEvent(event: String)
    }

    companion object Inner
    class Params(
        var colorText: Int = Color.BLUE,
        var colorBg: Int = Color.WHITE,
        var isUnderlined: Boolean = false,
        var styleText: Typeface = Typeface.DEFAULT
    )


}
