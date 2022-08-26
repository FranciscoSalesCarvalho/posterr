package com.francisco.strider.domain.models

import com.francisco.strider.dsc.extensions.EMPTY_STRING

data class Post(
    val name: String,
    val starQuantity: Long,
    val forkQuantity: Long,
    val imageUrl: String,
    val authorName: String
) {
    companion object {
        fun mock() = Post(
            name = "kotlin",
            authorName = "JetBrains",
            starQuantity = 1,
            forkQuantity = 1,
            imageUrl = EMPTY_STRING
        )
    }
}
