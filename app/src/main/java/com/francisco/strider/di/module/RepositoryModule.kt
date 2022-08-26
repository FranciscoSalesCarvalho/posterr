package com.francisco.strider.di.module

import com.francisco.strider.data.api.EventApi
import com.francisco.strider.data.repostiory.EventsRepositoryImpl
import com.francisco.strider.domain.repository.EventsRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class RepositoryModule {

    @Provides
    @Reusable
    fun provideGithubRepository(api: EventApi): EventsRepository {
        return EventsRepositoryImpl(api = api)
    }
}