package com.francisco.strider.commons.exception

import com.francisco.strider.commons.error.ErrorHandler

open class CustomException(
    val title: String = ErrorHandler.DEFAULT_TITLE,
    override val message: String,
    val code: String = ErrorHandler.UNEXPECTED_ERROR
) : Exception()
