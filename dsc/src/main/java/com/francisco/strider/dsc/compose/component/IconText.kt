package com.francisco.strider.dsc.compose.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.R
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.dimen.sp
import com.francisco.strider.dsc.compose.theme.font.FontIconKit

@Composable
fun IconText(
    fontIcon: String,
    iconSize: Dp,
    modifier: Modifier = Modifier,
    color: Color = ColorPalette.Support500,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily = FontIconKit
) {
    Text(
        modifier = modifier.testTag(IconTextTestTag.ICON_TEXT),
        text = fontIcon,
        color = color,
        fontSize = iconSize.sp(),
        fontFamily = fontFamily,
        fontWeight = fontWeight
    )
}

@Preview
@Composable
private fun IconTextPreview() {
    IconText(
        fontIcon = LocalContext.current.getString(R.string.icon_chat_kit),
        iconSize = Size.Size4XLG,
        color = ColorPalette.Primary200
    )
}

internal object IconTextTestTag {
    const val ICON_TEXT = "IconText"
}
