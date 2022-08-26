package com.francisco.strider.domain.repository

import com.francisco.strider.domain.models.Post

interface EventsRepository {

    suspend fun getPosts(): List<Post>
    suspend fun getPost(id: String): Post
    suspend fun checkIn(id: String)
}