package com.francisco.strider.dsc.compose.theme.font

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.francisco.strider.dsc.R
import com.francisco.strider.dsc.compose.theme.color.ColorPalette

val FontAverta = FontFamily(
    Font(R.font.avertastd_regular, FontWeight.Normal),
    Font(R.font.avertastd_light, FontWeight.Light),
    Font(R.font.avertastd_bold, FontWeight.Bold),
    Font(R.font.avertastd_semibold, FontWeight.SemiBold)
)

val FontIconKit = FontFamily(
    Font(R.font.fontawesomekit_regular, FontWeight.Normal)
)

val FontIconPro = FontFamily(
    Font(R.font.fontawesomepro_light, FontWeight.Normal)
)

val Font: Typography
    @Composable get() = Typography(
        defaultFontFamily = FontAverta,
        h1 = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.Bold,
            fontSize = FontSize.FontSize3XLG,
            lineHeight = LineHeight.LineHeight2XLG,
            color = ColorPalette.Support500
        ),
        h2 = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.SemiBold,
            fontSize = FontSize.FontSize2XLG,
            lineHeight = LineHeight.LineHeightXLG,
            color = ColorPalette.Support500
        ),
        h3 = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.Bold,
            fontSize = FontSize.FontSizeXLG,
            lineHeight = LineHeight.LineHeightLG,
            color = ColorPalette.Support500
        ),
        h4 = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.Normal,
            fontSize = FontSize.FontSizeLG,
            lineHeight = LineHeight.LineHeightMD,
            color = ColorPalette.Support500
        ),
        body1 = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.Normal,
            fontSize = FontSize.FontSizeMD,
            lineHeight = LineHeight.LineHeightMD,
            color = ColorPalette.Support500
        ),
        body2 = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.Normal,
            fontSize = FontSize.FontSizeSM,
            lineHeight = LineHeight.LineHeightSM,
            color = ColorPalette.Support500
        ),
        caption = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.Normal,
            fontSize = FontSize.FontSizeXSM,
            lineHeight = LineHeight.LineHeightXSM,
            color = ColorPalette.Support500
        ),
        overline = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.Normal,
            fontSize = FontSize.FontSize2XSM,
            lineHeight = LineHeight.LineHeightXSM,
            color = ColorPalette.Support500
        ),
        button = TextStyle(
            fontFamily = FontAverta,
            fontWeight = FontWeight.SemiBold,
            fontSize = FontSize.FontSizeMD,
            lineHeight = LineHeight.LineHeightMD,
            color = ColorPalette.Support100
        )
    )

inline val Typography.h4SemiBold: TextStyle
    get() = h4.copy(fontWeight = FontWeight.SemiBold)

inline val Typography.body1Bold: TextStyle
    get() = body1.copy(fontWeight = FontWeight.Bold)

inline val Typography.body1SemiBold: TextStyle
    get() = body1.copy(fontWeight = FontWeight.SemiBold)

inline val Typography.body2Bold: TextStyle
    get() = body2.copy(fontWeight = FontWeight.Bold)

inline val Typography.button2: TextStyle
    get() = caption.copy(fontWeight = FontWeight.SemiBold)

inline val Typography.password: TextStyle
    get() = body1.copy(letterSpacing = FontSize.FontSize2XLG)
