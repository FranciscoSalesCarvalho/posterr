package com.francisco.strider.commons.extensions

import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.error.ErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeRunDispatcher(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> (T)
) = withContext(dispatcher) {
    return@withContext try {
        val result = block()
        Result.Success(result)
    } catch (ex: Exception) {
        Result.Failure(ErrorHandler.convertError(ex))
    }
}

class SafeRunException(val error: Error) : Exception()

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Failure(val error: Error) : Result<Nothing>()
}