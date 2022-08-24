package com.francisco.strider.dsc.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Elevation
import com.francisco.strider.dsc.compose.theme.dimen.Line
import com.francisco.strider.dsc.compose.theme.dimen.ProgressSize
import com.francisco.strider.dsc.compose.theme.dimen.Radius
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.extensions.EMPTY_STRING

@Composable
fun Button(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    visible: Boolean = true,
    contentColor: Color? = null,
    textStyle: TextStyle = Font.button,
    buttonStyle: ButtonStyle = ButtonStyle.primary(),
    buttonWidth: ButtonWidth = ButtonWidth.wrap(),
    buttonHeight: ButtonHeight = ButtonHeight.normal(),
    buttonIcon: ButtonIcon = ButtonIcon.buttonIcon(),
    buttonIconArrangement: Arrangement.Horizontal = Arrangement.Center,
    progressText: String = EMPTY_STRING,
    progressType: ProgressAnimationType = ProgressAnimationType.CIRCLE_SUPPORT100,
    progress: Boolean = false,
    testTag: String = ButtonTestTag.BUTTON
) {
    if (visible.not()) return

    androidx.compose.material.Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(buttonHeight.height)
            .testTag(testTag),
        shape = RoundedCornerShape(buttonStyle.radius),
        border = buttonStyle.strokeColor?.let {
            BorderStroke(
                width = Line.LineMD,
                color = it
            )
        },
        elevation = if (buttonStyle.hasElevation) {
            ButtonDefaults.elevation()
        } else {
            ButtonDefaults.elevation(Elevation.Elevation0, Elevation.Elevation0)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonStyle.color,
            disabledBackgroundColor = buttonStyle.color,
            contentColor = ColorPalette.Primary100,
            disabledContentColor = ColorPalette.Primary100
        ),
        contentPadding = PaddingValues(
            horizontal = buttonStyle.horizontalPadding
        )
    ) {
        if (progress) {
            ButtonProgressContent(
                progressText = progressText,
                progressType = progressType,
                contentColor = contentColor,
                textStyle = textStyle,
                buttonStyle = buttonStyle,
                buttonWidth = buttonWidth
            )
        } else {
            ButtonContent(
                text = text,
                contentColor = contentColor,
                textStyle = textStyle,
                buttonStyle = buttonStyle,
                buttonWidth = buttonWidth,
                buttonIcon = buttonIcon,
                buttonIconArrangement = buttonIconArrangement
            )
        }

    }
}

@Composable
private fun ButtonContent(
    text: String,
    contentColor: Color?,
    textStyle: TextStyle,
    buttonStyle: ButtonStyle,
    buttonWidth: ButtonWidth,
    buttonIcon: ButtonIcon,
    buttonIconArrangement: Arrangement.Horizontal
) {
    val safeContentColor = contentColor ?: buttonStyle.contentColor

    Row(
        modifier = buttonWidth.modifier
            .testTag(ButtonTestTag.BUTTON_DEFAULT_CONTENT),
        horizontalArrangement = buttonIconArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (buttonIcon.hasIconStart()) {
            ButtonIcon(
                iconId = buttonIcon.iconStartId,
                fontIcon = buttonIcon.fontIconStart,
                iconColor = safeContentColor,
                iconSize = buttonIcon.size,
                testTag = ButtonTestTag.ICON_START
            )
            SpacerHorizontal(buttonIcon.spacing)
        }
        Text(
            text = text,
            style = textStyle,
            color = safeContentColor
        )
        if (buttonIcon.hasIconEnd()) {
            SpacerHorizontal(buttonIcon.spacing)
            ButtonIcon(
                iconId = buttonIcon.iconEndId,
                fontIcon = buttonIcon.fontIconEnd,
                iconColor = safeContentColor,
                iconSize = buttonIcon.size,
                testTag = ButtonTestTag.ICON_END
            )
        }
    }
}

@Composable
private fun ButtonProgressContent(
    progressText: String,
    progressType: ProgressAnimationType,
    contentColor: Color?,
    textStyle: TextStyle,
    buttonStyle: ButtonStyle,
    buttonWidth: ButtonWidth
) {
    Row(
        modifier = buttonWidth.modifier
            .testTag(ButtonTestTag.BUTTON_PROGRESS_CONTENT),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ProgressAnimation(
            modifier = androidx.compose.ui.Modifier.size(ProgressSize.ProgressSM),
            type = progressType
        )
        SpacerHorizontal(Size.SizeXSM)
        Text(
            text = progressText,
            style = textStyle,
            color = contentColor ?: buttonStyle.contentColor
        )
    }
}

@Composable
private fun ButtonIcon(
    iconId: Int?,
    fontIcon: String?,
    iconColor: Color,
    iconSize: Dp?,
    testTag: String
) {
    if (iconId != null) {
        Icon(
            modifier = (iconSize?.let { Modifier.size(it) } ?: Modifier)
                .testTag(testTag),
            painter = painterResource(iconId),
            contentDescription = null,
            tint = iconColor
        )
    } else if (fontIcon != null) {
        IconText(
            modifier = Modifier.testTag(testTag),
            fontIcon = fontIcon,
            iconSize = iconSize ?: Size.SizeMD,
            color = iconColor,
            fontWeight = FontWeight.Normal
        )
    }
}

data class ButtonIcon(
    val iconStartId: Int? = null,
    val iconEndId: Int? = null,
    val fontIconStart: String? = null,
    val fontIconEnd: String? = null,
    val spacing: Dp,
    val size: Dp?
) {

    fun hasIconStart() = iconStartId != null || fontIconStart != null

    fun hasIconEnd() = iconEndId != null || fontIconEnd != null

    companion object {

        @Deprecated(
            message = "Use font icon instead of vetor image in button icon.",
            replaceWith = ReplaceWith(expression = "ButtonIcon.buttonIconText(fontIconStart = , fontIconEnd = )")
        )
        @Composable
        fun buttonIcon(
            iconStartId: Int? = null,
            iconEndId: Int? = null,
            spacing: Dp = Size.SizeSM,
            size: Dp? = null
        ) = ButtonIcon(
            iconStartId = iconStartId,
            iconEndId = iconEndId,
            spacing = spacing,
            size = size
        )

        @Composable
        fun buttonIconText(
            fontIconStart: String? = null,
            fontIconEnd: String? = null,
            spacing: Dp = Size.SizeSM,
            size: Dp = Size.SizeMD
        ) = ButtonIcon(
            fontIconStart = fontIconStart,
            fontIconEnd = fontIconEnd,
            spacing = spacing,
            size = size
        )
    }
}

data class ButtonWidth(val modifier: Modifier) {
    companion object {
        @Composable
        fun fill() = ButtonWidth(Modifier.fillMaxSize())

        @Composable
        fun wrap() = ButtonWidth(Modifier.fillMaxHeight())
    }
}

data class ButtonHeight(val height: Dp) {
    companion object {
        @Composable
        fun normal() = ButtonHeight(Size.Size3XLG)

        @Composable
        fun small() = ButtonHeight(Size.SizeXLG)
    }
}

data class ButtonStyle(
    val color: Color,
    val contentColor: Color,
    val strokeColor: Color?,
    val radius: Dp,
    val horizontalPadding: Dp,
    val hasElevation: Boolean
) {
    companion object {
        @Composable
        fun primary() = ButtonStyle(
            color = ColorPalette.Primary200,
            contentColor = ColorPalette.Support100,
            strokeColor = null,
            radius = Radius.Radius2XLG,
            horizontalPadding = Size.SizeMD,
            hasElevation = true
        )

        @Composable
        fun secondary() = primary().copy(
            color = ColorPalette.Support100,
            contentColor = ColorPalette.Primary300
        )

        @Composable
        fun outlined() = primary().copy(
            color = ColorPalette.Transparent,
            contentColor = ColorPalette.Primary300,
            strokeColor = ColorPalette.Primary300,
            hasElevation = false
        )

        @Composable
        fun outlinedLight() = outlined().copy(
            contentColor = ColorPalette.Support100,
            strokeColor = ColorPalette.Support100
        )

        @Composable
        fun rectangle() = primary().copy(
            radius = Radius.Radius0
        )

        @Composable
        fun text() = primary().copy(
            color = ColorPalette.Transparent,
            contentColor = ColorPalette.Primary300,
            radius = Radius.Radius0,
            horizontalPadding = Size.Size2XSM,
            hasElevation = false
        )
    }
}

object ButtonTestTag {
    const val BUTTON = "Button"
    const val ICON_START = "Button-IconStart"
    const val ICON_END = "Button-IconEnd"
    const val BUTTON_DEFAULT_CONTENT = "Button-DefaultContent"
    const val BUTTON_PROGRESS_CONTENT = "Button-ProgressContent"
}