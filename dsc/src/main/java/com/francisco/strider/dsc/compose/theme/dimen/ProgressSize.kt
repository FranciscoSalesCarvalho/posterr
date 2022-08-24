package com.francisco.strider.dsc.compose.theme.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.francisco.strider.dsc.R

object ProgressSize {

    val ProgressSM: Dp @Composable get() = dimensionResource(id = R.dimen.progress_sm)
    val ProgressMD: Dp @Composable get() = dimensionResource(id = R.dimen.progress_md)
    val ProgressLG: Dp @Composable get() = dimensionResource(id = R.dimen.progress_lg)
    val ProgressXLG: Dp @Composable get() = dimensionResource(id = R.dimen.progress_xlg)
    val Progress2XLG: Dp @Composable get() = dimensionResource(id = R.dimen.progress_2xlg)

}
