package com.francisco.strider.dsc.compose.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size

@Composable
fun IconLabelValue(
    modifier: Modifier = Modifier,
    labelText: LabelValueText,
    valueText: LabelValueText? = null,
    fontIcon: String,
    iconSize: Dp = Size.SizeLG,
    iconColor: Color = ColorPalette.Primary200,
    spacerVertical: Dp = Size.Size0,
    spacerHorizontal: Dp = Size.SizeXSM
) {
    Row(modifier = modifier.fillMaxWidth()) {
        IconText(
            fontIcon = fontIcon,
            iconSize = iconSize,
            color = iconColor
        )
        SpacerHorizontal(spacerHorizontal)
        LabelValue(
            modifier = Modifier.align(Alignment.CenterVertically),
            orientation = LabelValueOrientation.VERTICAL,
            labelText = labelText,
            valueText = valueText,
            spacerBetween = spacerVertical
        )
    }
}