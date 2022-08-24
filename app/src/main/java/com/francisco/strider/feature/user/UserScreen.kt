package com.francisco.strider.feature.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.francisco.strider.R
import com.francisco.strider.commons.extensions.DATE_FORMAT_MMMM_YYYY
import com.francisco.strider.commons.extensions.format
import com.francisco.strider.data.entities.User
import com.francisco.strider.dsc.compose.component.Header
import com.francisco.strider.dsc.compose.component.LabelValue
import com.francisco.strider.dsc.compose.component.LabelValueOrientation
import com.francisco.strider.dsc.compose.component.LabelValueText
import com.francisco.strider.dsc.compose.theme.DscTheme
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.feature.commons.ScreenError
import com.francisco.strider.feature.commons.ScreenProgress
import com.francisco.strider.feature.main.MainActivity
import com.francisco.strider.feature.main.MainViewModel
import com.francisco.strider.feature.user.UserViewModel.ScreenEvent
import com.francisco.strider.feature.user.UserViewModel.ScreenState
import com.francisco.strider.feature.user.UserViewModel.UserScreenUiState

@Composable
fun UserScreen(
    viewModel: UserViewModel,
    flowViewModel: MainViewModel
) {

    val activity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel) {
        viewModel.setup()
    }

    EventConsumer(
        activity = activity,
        viewModel = viewModel,
        flowViewModel = flowViewModel
    )

    Screen(
        uiState = viewModel.uiState,
        onGoBackClicked = viewModel::onGoBackClicked,
        onRetryClicked = viewModel::setup
    )
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: UserViewModel,
    flowViewModel: MainViewModel
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                ScreenEvent.GoBack -> activity.onBackPressed()
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: UserScreenUiState,
    onGoBackClicked: () -> Unit,
    onRetryClicked: () -> Unit,
) {
    when (val result = uiState.screenState.collectAsState().value) {
        ScreenState.Loading -> ScreenProgress()
        is ScreenState.Failure -> ScreenError(
            error = result.error,
            onRetryClicked = onRetryClicked,
            onErrorCloseClicked = onGoBackClicked
        )
        is ScreenState.Success -> ScreenContent(
            user = result.user,
            onGoBackClicked = onGoBackClicked
        )

    }
}

@Composable
private fun ScreenContent(
    user: User,
    onGoBackClicked: () -> Unit,
) = DscTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.Support100)
            .verticalScroll(rememberScrollState()),
    ) {
        PageHeader(onGoBackClicked = onGoBackClicked)
        Column(modifier = Modifier.padding(all = Size.SizeSM)) {
            UserInfo(user = user)
        }
    }
}

@Composable
private fun PageHeader(
    onGoBackClicked: () -> Unit,
) {
    Header(
        title = stringResource(id = R.string.user),
        fontIconStart = stringResource(id = com.francisco.strider.dsc.R.string.icon_chevron_left_kit),
        fontIconStartClick = onGoBackClicked,
    )
}

@Composable
private fun UserInfo(
    user: User
) {
    LabelValue(
        orientation = LabelValueOrientation.VERTICAL,
        spacerBetween = Size.SizeMD,
        labelText = LabelValueText(
            text = user.username,
            textStyle = Font.h3
        ),
        valueText = LabelValueText(
            text = user.dateJoined.format(DATE_FORMAT_MMMM_YYYY),
            textStyle = Font.body1
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen(
        uiState = UserScreenUiState(),
        onGoBackClicked = {},
        onRetryClicked = {}
    )
}
