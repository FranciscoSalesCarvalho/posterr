package com.francisco.strider.data.mapper

import com.francisco.strider.data.entities.Github
import com.francisco.strider.domain.models.Post

object ItemMapper {

    fun Github.toPosts() =
        this.items?.map {
            Post(
                name = it.name.orEmpty(),
                forkQuantity = it.forksCount ?: 0L,
                starQuantity = it.stargazersCount ?: 0L,
                imageUrl = it.owner?.avatarUrl.orEmpty(),
                authorName = it.owner?.login.orEmpty()
            )
        } ?: listOf()
}