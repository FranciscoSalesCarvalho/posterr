package com.francisco.strider.feature.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.francisco.strider.R
import com.francisco.strider.dsc.compose.component.Button
import com.francisco.strider.dsc.compose.component.ButtonWidth
import com.francisco.strider.dsc.compose.component.Header
import com.francisco.strider.dsc.compose.component.IconText
import com.francisco.strider.dsc.compose.theme.DscTheme
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.dimen.Weight1
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.extensions.EMPTY_STRING
import com.francisco.strider.feature.main.MainActivity
import com.francisco.strider.feature.main.MainViewModel

@Composable
fun SuccessScreen(
    viewModel: SuccessViewModel,
    flowViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity

    EventConsumer(activity = activity, viewModel = viewModel, flowViewModel = flowViewModel)

    Screen(
        onClickHeaderClose = viewModel::onCloseClicked,
        onClickButtonUpdate = viewModel::onCloseClicked
    )

}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: SuccessViewModel,
    flowViewModel: MainViewModel,
) = LaunchedEffect(key1 = viewModel) {
    viewModel.eventsFlow.collect { event ->
        when (event) {
            SuccessViewModel.ScreenEvent.Finish -> activity.finish()
        }
    }
}

@Composable
private fun Screen(
    onClickHeaderClose: () -> Unit,
    onClickButtonUpdate: () -> Unit
) = DscTheme {
    Column(Modifier.background(ColorPalette.Support100)) {
        PageHeader(onClickHeader = onClickHeaderClose)
        IconText(
            fontIcon = stringResource(id = com.francisco.strider.dsc.R.string.icon_hifive_kit),
            iconSize = Size.Size3XLG,
            color = ColorPalette.Primary200,
            modifier = Modifier.padding(
                top = Size.SizeSM,
                start = Size.SizeSM,
                end = Size.SizeSM
            )
        )
        Title()
        Spacer(modifier = Modifier.weight(Weight1))
        PageButton(onClickButton = onClickButtonUpdate)
    }
}

@Composable
private fun PageHeader(onClickHeader: () -> Unit) = Header(
    title = EMPTY_STRING,
    fontIconEnd = LocalContext.current.getString(com.francisco.strider.dsc.R.string.icon_times_kit),
    fontIconEndClick = { onClickHeader.invoke() }
)

@Composable
private fun Title() = Text(
    text = "CheckIn Done",
    modifier = Modifier.padding(
        top = Size.SizeSM,
        start = Size.SizeSM,
        end = Size.SizeSM,
        bottom = Size.SizeMD
    ),
    style = Font.h3
)

@Composable
private fun PageButton(onClickButton: () -> Unit) = Button(
    onClick = { onClickButton.invoke() },
    text = "Concluir",
    buttonWidth = ButtonWidth.fill(),
    modifier = Modifier.padding(
        start = Size.SizeSM,
        end = Size.SizeSM,
        bottom = Size.SizeSM,
        top = Size.SizeMD
    )
)
