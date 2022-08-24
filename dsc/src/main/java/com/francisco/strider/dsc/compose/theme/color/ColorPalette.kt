package com.francisco.strider.dsc.compose.theme.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.francisco.strider.dsc.R

object ColorPalette {

    val Transparent @Composable get() = colorResource(id = android.R.color.transparent)

    val Primary400 @Composable get() = colorResource(id = R.color.primary400)
    val Primary300 @Composable get() = colorResource(id = R.color.primary300)
    val Primary200 @Composable get() = colorResource(id = R.color.primary200)
    val Primary100 @Composable get() = colorResource(id = R.color.primary100)
    val Primary50 @Composable get() = colorResource(id = R.color.primary50)

    val Secondary300 @Composable get() = colorResource(id = R.color.secondary300)
    val Secondary200 @Composable get() = colorResource(id = R.color.secondary200)
    val Secondary100 @Composable get() = colorResource(id = R.color.secondary100)

    val Support500 @Composable get() = colorResource(id = R.color.support500)
    val Support400 @Composable get() = colorResource(id = R.color.support400)
    val Support300 @Composable get() = colorResource(id = R.color.support300)
    val Support200 @Composable get() = colorResource(id = R.color.support200)
    val Support150 @Composable get() = colorResource(id = R.color.support150)
    val Support100 @Composable get() = colorResource(id = R.color.support100)

    val Success300 @Composable get() = colorResource(id = R.color.success300)
    val Success200 @Composable get() = colorResource(id = R.color.success200)
    val Success100 @Composable get() = colorResource(id = R.color.success100)

    val Alert300 @Composable get() = colorResource(id = R.color.alert300)
    val Alert200 @Composable get() = colorResource(id = R.color.alert200)
    val Alert100 @Composable get() = colorResource(id = R.color.alert100)

    val Critical300 @Composable get() = colorResource(id = R.color.critical300)
    val Critical200 @Composable get() = colorResource(id = R.color.critical200)
    val Critical100 @Composable get() = colorResource(id = R.color.critical100)
}
