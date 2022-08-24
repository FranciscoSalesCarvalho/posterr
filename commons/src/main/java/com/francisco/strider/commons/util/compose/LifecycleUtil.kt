package com.francisco.strider.commons.util.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun GetLifeCycleEvent(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_START,
    onEventCalled: () -> Unit,
) {
    val currentEvent by rememberUpdatedState(onEventCalled)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == lifecycleEvent) currentEvent()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}