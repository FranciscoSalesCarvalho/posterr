package com.francisco.strider.data.mapper

import com.francisco.strider.data.entities.Event
import com.francisco.strider.domain.models.Post

object EventMapper {

    fun List<Event>.toPosts() =
        this.map {
            Post(
                id = it.id.orEmpty(),
                title = it.title.orEmpty(),
                image = it.image.orEmpty(),
                description = it.description.orEmpty(),
                date = it.date ?: 0
            )
        }

    fun Event.toPost() =
        Post(
            id = id.orEmpty(),
            title = title.orEmpty(),
            image = image.orEmpty(),
            description = description.orEmpty(),
            date = date ?: 0
        )
}