package com.francisco.strider.dsc.compose.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.francisco.strider.dsc.R
import com.francisco.strider.dsc.compose.theme.dimen.Size

@Composable
fun ProgressAnimation(
    modifier: Modifier = Modifier,
    type: ProgressAnimationType
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(type.animation))
    LottieAnimation(
        composition = composition,
        modifier = modifier.testTag(ProgressAnimationTestTag.PROGRESS),
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun ProgressAnimationMessage(
    modifier: Modifier = Modifier,
    type: ProgressAnimationType,
    message: LabelValueText,
    description: LabelValueText? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressAnimation(
            modifier = modifier,
            type = type
        )
        SpacerVertical(Size.SizeMD)
        LabelValue(
            modifier = Modifier.fillMaxWidth(),
            orientation = LabelValueOrientation.VERTICAL,
            labelText = message,
            valueText = description,
            textAlign = TextAlign.Center
        )
    }
}

enum class ProgressAnimationType(@RawRes val animation: Int) {
    CIRCLE_PRIMARY300(R.raw.lottie_progress_circle_primary300),
    CIRCLE_PRIMARY200(R.raw.lottie_progress_circle_primary200),
    CIRCLE_SUPPORT100(R.raw.lottie_progress_circle_support100),
    COG_PRIMARY200(R.raw.lottie_progress_cog_primary200),
    SEARCH_PRIMARY200(R.raw.lottie_progress_search_primary200)
}

object ProgressAnimationTestTag {
    const val PROGRESS = "ProgressAnimation-Progress"
}
