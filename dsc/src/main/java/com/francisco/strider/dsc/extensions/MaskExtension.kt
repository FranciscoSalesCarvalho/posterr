package com.francisco.strider.dsc.extensions

private const val SIGN_REGEX = "[.\\-/(), ]"

internal fun String.isMaskSign(position: Int) = getOrNull(position)
    ?.let { char -> SIGN_REGEX.toRegex().matches(char.toString()) }
    ?: false