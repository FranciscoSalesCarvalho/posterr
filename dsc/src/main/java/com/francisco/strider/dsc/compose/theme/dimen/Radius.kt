package com.francisco.strider.dsc.compose.theme.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.R

object Radius {

    val Radius0: Dp @Composable get() = dimensionResource(id = R.dimen.size_0)
    val RadiusSM: Dp @Composable get() = dimensionResource(id = R.dimen.radius_sm)
    val RadiusMD: Dp @Composable get() = dimensionResource(id = R.dimen.radius_md)
    val RadiusLG: Dp @Composable get() = dimensionResource(id = R.dimen.radius_lg)
    val RadiusXLG: Dp @Composable get() = dimensionResource(id = R.dimen.radius_xlg)
    val Radius2XLG: Dp @Composable get() = dimensionResource(id = R.dimen.radius_xxlg)

}
