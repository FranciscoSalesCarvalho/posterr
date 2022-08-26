package com.francisco.strider.domain.repository

import androidx.paging.PagingData
import com.francisco.strider.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getRepositories(): Flow<PagingData<Post>>
}