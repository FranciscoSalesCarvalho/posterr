package com.francisco.strider.data.repostiory

import com.francisco.strider.data.database.dao.PostDao
import com.francisco.strider.data.entities.Post
import com.francisco.strider.domain.repository.PostRepository
import java.util.Date
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dao: PostDao
) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        return dao.getPosts()
    }

    override suspend fun savePosts(post: String) {
        val post = Post(message = post, creation = Date())
        return dao.insert(post = post)
    }
}