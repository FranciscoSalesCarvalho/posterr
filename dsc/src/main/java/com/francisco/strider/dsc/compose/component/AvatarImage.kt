package com.francisco.strider.dsc.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.compose.theme.font.body1SemiBold

@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    initials: String,
    initialsTextStyle: TextStyle = Font.body1SemiBold,
    initialsColor: Color = ColorPalette.Primary200,
    backgroundColor: Color = ColorPalette.Support300
) {
    Box(
        modifier = modifier.background(
            color = backgroundColor,
            shape = CircleShape
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = initialsTextStyle,
            color = initialsColor
        )
        imageUrl?.let {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(AvatarImageTestTag.IMAGE),
                painter = rememberImagePainter(
                    data = it,
                    builder = { transformations(CircleCropTransformation()) }
                ),
                contentDescription = null
            )
        }
    }
}

object AvatarImageTestTag {
    const val IMAGE = "AvatarImage-Image"
}

@Preview(showBackground = true)
@Composable
private fun AvatarImagePreview() {
    AvatarImage(
        modifier = Modifier.size(Size.SizeXLG),
        imageUrl = null,
        initials = "AI"
    )
}