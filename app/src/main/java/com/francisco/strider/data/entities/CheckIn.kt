package com.francisco.strider.data.entities

data class CheckIn(
    val id: String,
    val name: String,
    val email: String,
) {
    companion object {
        fun mock(id: String) = CheckIn(id = id, name = "Francisco Sales", email = "s@s.com")
    }
}
