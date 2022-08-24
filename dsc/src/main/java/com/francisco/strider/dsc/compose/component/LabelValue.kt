package com.francisco.strider.dsc.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.compose.component.LabelValueOrientation.*
import com.francisco.strider.dsc.compose.theme.dimen.Size

@Composable
fun LabelValue(
    modifier: Modifier = Modifier,
    orientation: LabelValueOrientation,
    labelText: LabelValueText,
    valueText: LabelValueText? = null,
    textAlign: TextAlign? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    spacerBetween: Dp = Size.Size2XSM
) {
    val content: @Composable () -> Unit = {
        val textModifier = if (orientation == VERTICAL) {
            Modifier.fillMaxWidth()
        } else {
            Modifier
        }

        Text(
            modifier = textModifier.testTag(LabelValueTestTag.LABEL),
            text = labelText.text,
            style = labelText.textStyle,
            color = labelText.color,
            textAlign = textAlign
        )

        valueText?.run {
            when (orientation) {
                VERTICAL -> SpacerVertical(spacerBetween)
                HORIZONTAL -> SpacerHorizontal(spacerBetween)
            }

            Text(
                modifier = textModifier.testTag(LabelValueTestTag.VALUE),
                text = text,
                style = textStyle,
                color = color,
                textAlign = textAlign
            )
        }
    }

    when (orientation) {
        VERTICAL -> Column(
            modifier = modifier.testTag(LabelValueTestTag.COLUMN),
            verticalArrangement = verticalArrangement
        ) {
            content.invoke()
        }
        HORIZONTAL -> Row(
            modifier = modifier.testTag(LabelValueTestTag.ROW),
            horizontalArrangement = horizontalArrangement
        ) {
            content.invoke()
        }
    }
}

data class LabelValueText(
    val text: String,
    val textStyle: TextStyle,
    val color: Color = textStyle.color
)

enum class LabelValueOrientation {
    VERTICAL,
    HORIZONTAL
}

object LabelValueTestTag {
    const val LABEL = "LabelValue-Label"
    const val VALUE = "LabelValue-Value"
    const val COLUMN = "LabelValue-Column"
    const val ROW = "LabelValue-Row"
}