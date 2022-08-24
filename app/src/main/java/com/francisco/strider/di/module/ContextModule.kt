package com.francisco.strider.di.module

import android.content.Context
import com.francisco.strider.PosterrApplication
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule {

    @Binds
    abstract fun provideContext(application: PosterrApplication) : Context
}