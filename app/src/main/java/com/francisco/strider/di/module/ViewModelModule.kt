package com.francisco.strider.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.francisco.strider.commons.viewModel.ViewModelFactory
import com.francisco.strider.commons.viewModel.ViewModelKey
import com.francisco.strider.feature.home.HomeViewModel
import com.francisco.strider.feature.main.MainViewModel
import com.francisco.strider.feature.poster.PosterViewModel
import com.francisco.strider.feature.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PosterViewModel::class)
    internal abstract fun bindPosterViewModel(viewModel: PosterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel
}