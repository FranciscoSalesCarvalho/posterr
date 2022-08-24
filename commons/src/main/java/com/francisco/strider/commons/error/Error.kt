package com.francisco.strider.commons.error

data class Error(
    val code: String = "",
    val identifier: String = "",
    val title: String = "",
    val message: String = "",
    val instructions: String = "",
    val messageArgs: Map<String, String> = emptyMap(),
    val redirectUrl: String = "",
) {

    companion object {
        fun mock() = Error(
            code = "401",
            identifier = "identifier",
            title = "error",
            message = "there was an error"
        )
    }
}