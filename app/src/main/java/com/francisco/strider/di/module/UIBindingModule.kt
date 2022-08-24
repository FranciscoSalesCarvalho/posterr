package com.francisco.strider.di.module

import com.francisco.strider.feature.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIBindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}