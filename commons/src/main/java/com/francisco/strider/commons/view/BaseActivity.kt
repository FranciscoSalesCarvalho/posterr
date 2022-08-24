package com.francisco.strider.commons.view

import android.content.Context
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.modifyContextConfig())
    }

    private fun Context.modifyContextConfig(): Context {
        val config = resources.configuration.apply {
            fontScale = if (fontScale > MAX_FONT_SCALE) MAX_FONT_SCALE else fontScale
        }
        return createConfigurationContext(config)
    }

    private companion object {
        /** This is Android SO (pure) font scale max value. **/
        const val MAX_FONT_SCALE = 1.3f
    }

}
