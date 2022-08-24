package com.francisco.strider.di.module

import android.app.Application
import com.francisco.strider.data.database.AppDatabase
import com.francisco.strider.data.database.dao.PostDao
import com.francisco.strider.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase =
        AppDatabase.getInstance(application)

    @Provides
    @Singleton
    fun providePostDao(appDatabase: AppDatabase): PostDao =
        appDatabase.postDao

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao =
        appDatabase.userDao
}