package com.francisco.strider.commons.extensions

import android.app.Activity
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils

fun Activity.setStatusBarColor(@ColorRes id: Int) {
    with(window) {
        if (ColorUtils.calculateLuminance(ContextCompat.getColor(this@setStatusBarColor, id)) < .5) {
            this.decorView.systemUiVisibility = 0
        } else {
            this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        this.statusBarColor = ContextCompat.getColor(this@setStatusBarColor, id)
    }
}