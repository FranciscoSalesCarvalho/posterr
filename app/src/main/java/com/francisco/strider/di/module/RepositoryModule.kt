package com.francisco.strider.di.module

import com.francisco.strider.data.database.dao.PostDao
import com.francisco.strider.data.database.dao.UserDao
import com.francisco.strider.data.repostiory.PostRepositoryImpl
import com.francisco.strider.data.repostiory.UserRepositoryImpl
import com.francisco.strider.domain.repository.PostRepository
import com.francisco.strider.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class RepositoryModule {

    @Provides
    @Reusable
    fun providePostRepository(postDao: PostDao): PostRepository {
        return PostRepositoryImpl(postDao)
    }

    @Provides
    @Reusable
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}