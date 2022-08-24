package com.francisco.strider.commons.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy"
const val DATE_FORMAT_MMMM_YYYY = "MMMM dd, yyyy"

fun Date.format(format: String): String {
    return SimpleDateFormat(format, Locale.US).format(this).trim().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.US
        ) else it.toString()
    }
}

fun Date.formatDateAsDayMonthYear() = format(DATE_FORMAT_DD_MM_YYYY)

fun checkDateEqualsToday(date: String?): Boolean {
    return if (date != null) {
        val calendar = Calendar.getInstance()
        val now = calendar.time.formatDateAsDayMonthYear()
        date == now
    } else {
        false
    }
}
