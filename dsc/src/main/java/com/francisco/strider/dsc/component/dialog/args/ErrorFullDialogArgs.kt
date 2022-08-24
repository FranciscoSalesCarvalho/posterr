package com.francisco.strider.dsc.component.dialog.args

import android.graphics.drawable.Drawable

data class ErrorFullDialogArgs(
    var icon: String = "",
    var title: String = "",
    var message: String = "",
    var topButtonText: String = "",
    var topButtonIcon: Drawable? = null,
    var onTopButtonClick: () -> Unit = {},
    var bottomButtonText: String = "",
    var onBottomButtonClick: () -> Unit = {},
    var onCloseClick: () -> Unit = {},
    var cancelable: Boolean = true
)
