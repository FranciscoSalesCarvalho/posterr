package com.francisco.strider.domain.repository

import com.francisco.strider.data.entities.Post

interface PostRepository {

    suspend fun getPosts(): List<Post>
    suspend fun savePosts(post: String)
}