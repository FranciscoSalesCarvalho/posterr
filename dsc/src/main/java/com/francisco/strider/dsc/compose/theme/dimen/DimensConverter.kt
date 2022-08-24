package com.francisco.strider.dsc.compose.theme.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

/**
 * This is used in case where we don't want to trigger font accessibility. (Ex: FontIcons)
 */
@Composable
fun Dp.sp() = with(LocalDensity.current) { this@sp.toSp() }