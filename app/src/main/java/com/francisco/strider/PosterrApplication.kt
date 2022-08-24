package com.francisco.strider

import com.francisco.strider.di.DaggerPosterrComponent
import com.francisco.strider.di.PosterrComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class PosterrApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: PosterrComponent = DaggerPosterrComponent.builder()
            .application(this)
            .build()

        component.inject(this)

        return component
    }

}