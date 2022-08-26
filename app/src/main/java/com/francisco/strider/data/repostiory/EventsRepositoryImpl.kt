package com.francisco.strider.data.repostiory

import com.francisco.strider.data.api.EventApi
import com.francisco.strider.data.entities.CheckIn
import com.francisco.strider.data.mapper.EventMapper.toPost
import com.francisco.strider.data.mapper.EventMapper.toPosts
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.repository.EventsRepository
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val api: EventApi
) : EventsRepository {

    override suspend fun getPosts(): List<Post> {
        return api.getEvents().toPosts()
    }

    override suspend fun getPost(id: String): Post {
        return api.getEvent(id = id).toPost()
    }

    override suspend fun checkIn(id: String) {
        val checkIn = CheckIn.mock(id)
        api.checkIn(checkIn = checkIn)
    }
}