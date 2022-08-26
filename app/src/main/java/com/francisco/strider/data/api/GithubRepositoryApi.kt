package com.francisco.strider.data.api

import com.francisco.strider.data.entities.Github
import retrofit2.http.GET
import retrofit2.http.Query

object QueryParameters{
    const val q = "language:kotlin"
    const val sort = "stars"
    const val page = 1
}

interface GithubRepositoryApi {
    @GET("repositories")
    suspend fun getRepositories(
        @Query("q") q: String = QueryParameters.q,
        @Query("sort") sort: String = QueryParameters.sort,
        @Query("page") page: Int = QueryParameters.page,
    ): Github
}