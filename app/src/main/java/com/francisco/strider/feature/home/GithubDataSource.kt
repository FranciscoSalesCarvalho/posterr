package com.francisco.strider.feature.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.francisco.strider.data.api.GithubRepositoryApi
import com.francisco.strider.data.mapper.ItemMapper.toPosts
import com.francisco.strider.domain.models.Post
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GithubDataSource(
    private val api: GithubRepositoryApi
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val result = api.getRepositories(page = params.key ?: STARTING_PAGE_INDEX).toPosts()
            LoadResult.Page(
                data = result,
                prevKey = params.key,
                nextKey = params.key?.plus(1) ?: STARTING_PAGE_INDEX.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}