package com.francisco.strider.dsc.compose.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import kotlin.math.absoluteValue

private const val MIN_PROGRESS_PERCENT = 0f
private const val MAX_PROGRESS_PERCENT = 1f

@Composable
fun LinearProgressIndicatorStep(
    modifier: Modifier = Modifier,
    progressMax: Int,
    progressStep: Int = MIN_PROGRESS_PERCENT.toInt(),
    color: Color = ColorPalette.Primary200,
    backgroundColor: Color = ColorPalette.Primary50,
    onProgressChanged: (percent: Float) -> Unit = {}
) {
    val progress: Float by animateFloatAsState(
        targetValue = (progressStep.toFloat() / progressMax.toFloat()) * MAX_PROGRESS_PERCENT
    )

    LinearProgressIndicator(
        modifier = modifier
            .testTag(LinearProgressIndicatorStepTestTag.PROGRESS.format(progress)),
        progress = progress,
        color = color,
        backgroundColor = backgroundColor
    )
    onProgressChanged(progress.absoluteValue)
}

object LinearProgressIndicatorStepTestTag {
    const val PROGRESS = "Progress-%s"
}