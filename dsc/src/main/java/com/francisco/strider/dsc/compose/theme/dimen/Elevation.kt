package com.francisco.strider.dsc.compose.theme.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.R

object Elevation {

    val Elevation0: Dp @Composable get() = dimensionResource(id = R.dimen.size_0)
    val Elevation2XSM: Dp @Composable get() = dimensionResource(id = R.dimen.elevation_2xsm)
    val ElevationXSM: Dp @Composable get() = dimensionResource(id = R.dimen.elevation_xsm)
    val ElevationSM: Dp @Composable get() = dimensionResource(id = R.dimen.elevation_sm)
    val ElevationMD: Dp @Composable get() = dimensionResource(id = R.dimen.elevation_md)
    val ElevationLG: Dp @Composable get() = dimensionResource(id = R.dimen.elevation_lg)

}
