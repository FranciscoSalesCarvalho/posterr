package com.francisco.strider.di.module

import com.francisco.strider.data.api.GithubRepositoryApi
import com.francisco.strider.data.repostiory.GithubRepositoryImpl
import com.francisco.strider.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Reusable
    fun provideGithubRepository(api: GithubRepositoryApi): GithubRepository {
        return GithubRepositoryImpl(api = api)
    }
}