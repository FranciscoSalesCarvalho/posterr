package com.francisco.strider.di

import android.app.Application
import com.francisco.strider.data.repostiory.PostRepositoryImpl
import com.francisco.strider.di.module.ContextModule
import com.francisco.strider.di.module.DatabaseModule
import com.francisco.strider.di.module.RepositoryModule
import com.francisco.strider.di.module.UIBindingModule
import com.francisco.strider.di.module.ViewModelModule
import com.francisco.strider.feature.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    ViewModelModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    AndroidSupportInjectionModule::class,
    UIBindingModule::class
])
interface PosterrComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): PosterrComponent
    }
}