package com.francisco.strider.dsc.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

const val SPLIT_CHARACTER = "#"
const val EMPTY_STRING = ""
const val BLANK_CHAR = ' '
const val WITHOUT_FONT_ID = 0
const val WITHOUT_COLOR_ID = 0
const val WITHOUT_RELATIVE_SIZE = 0f
const val WITHOUT_ABSOLUTE_SIZE = 0
const val START_SPAN = 0
const val EVEN_MOD_OPERATOR = 2
const val EVEN_RESULT = 0

fun TextView.setCustomText(
    text: String?,
    @FontRes font: Int,
    @ColorRes color: Int = WITHOUT_COLOR_ID,
    relativeSize: Float = WITHOUT_RELATIVE_SIZE,
    absoluteSize: Int = WITHOUT_ABSOLUTE_SIZE
) {
    val context = this.context

    val typeface = if (font != WITHOUT_FONT_ID)
        ResourcesCompat.getFont(context, font)
    else
        this.typeface

    var spanned: Spanned = SpannableString(EMPTY_STRING)
    val split = text?.split(SPLIT_CHARACTER.toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        ?: this.text.toString().split(SPLIT_CHARACTER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    for (i in split.indices) {
        when {
            i % EVEN_MOD_OPERATOR > EVEN_RESULT -> {
                val spannableString = SpannableString(split[i])
                spannableString.run {
                    /* COLOR */
                    if (color != WITHOUT_COLOR_ID) {
                        setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), START_SPAN, split[i].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }

                    /* TYPEFACE */
                    typeface?.let {
                        setSpan(CustomTypefaceSpan(typeface), START_SPAN, split[i].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }

                    /* RELATIVE SIZE */
                    if (relativeSize != WITHOUT_RELATIVE_SIZE) {
                        setSpan(RelativeSizeSpan(relativeSize), START_SPAN, split[i].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }

                    /* ABSOLUTE SIZE */
                    if (absoluteSize != WITHOUT_ABSOLUTE_SIZE) {
                        setSpan(AbsoluteSizeSpan(absoluteSize, true), START_SPAN, split[i].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }

                spanned = TextUtils.concat(spanned, spannableString) as Spanned
            }
            else -> {
                spanned = TextUtils.concat(spanned, SpannableString(split[i])) as Spanned
            }
        }
    }

    this.text = spanned
}

