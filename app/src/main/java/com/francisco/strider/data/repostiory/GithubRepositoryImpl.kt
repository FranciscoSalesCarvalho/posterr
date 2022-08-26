package com.francisco.strider.data.repostiory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.francisco.strider.data.api.GithubRepositoryApi
import com.francisco.strider.data.entities.Github
import com.francisco.strider.data.mapper.ItemMapper.toPosts
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.repository.GithubRepository
import com.francisco.strider.feature.home.GithubDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubRepositoryApi
) : GithubRepository {

    override fun getRepositories(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 500),
            pagingSourceFactory = { GithubDataSource(api) }
        ).flow
    }
}