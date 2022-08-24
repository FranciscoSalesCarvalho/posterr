package com.francisco.strider.dsc.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.font.Font

@Composable
fun DscTheme(content: @Composable () -> Unit) {
    val lightColors = lightColors(
        primary = ColorPalette.Primary200,
        primaryVariant = ColorPalette.Primary300,
        onPrimary = ColorPalette.Support100,
        secondary = ColorPalette.Secondary200,
        secondaryVariant = ColorPalette.Secondary300,
        onSecondary = ColorPalette.Support100,
        error = ColorPalette.Critical200,
        onError = ColorPalette.Support100,
        background = ColorPalette.Support200,
        onBackground = ColorPalette.Support500,
        surface = ColorPalette.Support100,
        onSurface = ColorPalette.Support500
    )

    MaterialTheme(
        colors = lightColors,
        typography = Font,
        content = content
    )
}

