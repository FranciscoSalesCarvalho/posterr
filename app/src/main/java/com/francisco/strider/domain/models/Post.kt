package com.francisco.strider.domain.models

data class Post(
    val id: String,
    val image: String,
    val title: String,
    val date: Long,
    val description: String,
) {
    companion object {
        fun mock() = Post(
            id = "1",
            image = "https://",
            title = "title",
            date = 0,
            description = "description"
        )
    }
}