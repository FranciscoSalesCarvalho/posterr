package com.francisco.strider.commons.util.keyboard

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView

private const val MIN_HEIGHT = 0.15

@Composable
fun isKeyboardOpenAsState(): State<Boolean> {
    val isKeyboardOpen = remember { mutableStateOf(false) }

    LocalView.current.let { view ->
        DisposableEffect(view) {
            ViewTreeObserver.OnGlobalLayoutListener {
                val rect = Rect().also { view.getWindowVisibleDisplayFrame(it) }
                val screenHeight = view.rootView.height
                val keypadHeight = screenHeight - rect.bottom
                isKeyboardOpen.value = keypadHeight > screenHeight * MIN_HEIGHT
            }.let { listener ->
                view.viewTreeObserver.addOnGlobalLayoutListener(listener)
                onDispose { view.viewTreeObserver.removeOnGlobalLayoutListener(listener) }
            }
        }
    }

    return isKeyboardOpen
}