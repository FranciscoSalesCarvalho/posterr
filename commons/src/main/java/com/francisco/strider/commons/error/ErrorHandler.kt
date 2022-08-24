package com.francisco.strider.commons.error

import com.francisco.strider.commons.exception.CustomException
import com.francisco.strider.commons.extensions.SafeRunException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {

    const val UNEXPECTED_ERROR = "UNEXPECTED_ERROR"
    const val TIMEOUT_ERROR = "TIMEOUT_ERROR"
    const val CONNECTIVITY_ERROR = "CONNECTIVITY_ERROR"
    const val DEFAULT_TITLE = "Ops, ocorreu um erro!"

    private val default = Error(
        title = DEFAULT_TITLE,
        message = "Communication failure. Try again later",
        code = UNEXPECTED_ERROR
    )

    val timeOutError = Error(
        title = DEFAULT_TITLE,
        message = "Server connection time expired, verify your internet connection.",
        code = TIMEOUT_ERROR
    )

    private val connectivityError = Error(
        title = DEFAULT_TITLE,
        message = "Unable to connect to the server, please check your internet connection.",
        code = CONNECTIVITY_ERROR
    )

    fun convertError(error: Any): Error = when (error) {
        is SocketTimeoutException -> timeOutError
        is InterruptedIOException -> timeOutError
        is UnknownHostException -> connectivityError
        is SafeRunException -> error.error
        is CustomException ->  error.convertCustomException()
        else -> default
    }

    private fun CustomException.convertCustomException() = Error(
        title = this.title,
        message = this.message,
        code = this.code
    )
}
