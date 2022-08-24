package com.francisco.strider.dsc.compose.theme.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.R

object Line {

    val LineSM: Dp @Composable get() = dimensionResource(id = R.dimen.line_sm)
    val LineMD: Dp @Composable get() = dimensionResource(id = R.dimen.line_md)
    val LineLG: Dp @Composable get() = dimensionResource(id = R.dimen.line_lg)
}
