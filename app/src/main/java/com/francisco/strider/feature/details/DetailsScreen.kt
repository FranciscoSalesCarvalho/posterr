package com.francisco.strider.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.francisco.strider.R
import com.francisco.strider.commons.extensions.formatDateAsDayMonthYear
import com.francisco.strider.domain.models.Post
import com.francisco.strider.dsc.compose.component.AvatarImage
import com.francisco.strider.dsc.compose.component.Button
import com.francisco.strider.dsc.compose.component.ButtonIcon
import com.francisco.strider.dsc.compose.component.ButtonWidth
import com.francisco.strider.dsc.compose.component.Header
import com.francisco.strider.dsc.compose.component.SpacerHorizontal
import com.francisco.strider.dsc.compose.component.SpacerVertical
import com.francisco.strider.dsc.compose.theme.DscTheme
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.dimen.Weight1
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.compose.theme.font.body1Bold
import com.francisco.strider.dsc.extensions.EMPTY_STRING
import com.francisco.strider.feature.commons.ScreenError
import com.francisco.strider.feature.commons.ScreenProgress
import com.francisco.strider.feature.commons.showScreenErrorDialog
import com.francisco.strider.feature.main.MainActivity
import com.francisco.strider.feature.main.MainViewModel
import java.util.Date

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    flowViewModel: MainViewModel
) {

    val activity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel) {
        viewModel.setup(flowViewModel.itemSelectedId)
    }

    EventConsumer(
        activity = activity,
        viewModel = viewModel,
        flowViewModel = flowViewModel
    )

    Screen(
        uiState = viewModel.uiState,
        onGoBackClicked = viewModel::onGoBackClicked,
        onRetryClicked = viewModel::fetchPost,
        onCheckInClicked = viewModel::checkIn
    )
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: DetailsViewModel,
    flowViewModel: MainViewModel
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                DetailsViewModel.ScreenEvent.GoBack -> activity.onBackPressed()
                is DetailsViewModel.ScreenEvent.NavigateTo -> flowViewModel.navigate(event.navigation)
                is DetailsViewModel.ScreenEvent.Failure -> activity.showScreenErrorDialog(
                    error = event.error
                )
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: DetailsViewModel.DetailsScreenUiState,
    onGoBackClicked: () -> Unit,
    onRetryClicked: () -> Unit,
    onCheckInClicked: () -> Unit,
) = DscTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.Support100)
    ) {
        PageHeader()
        when (val result = uiState.screenState.collectAsState().value) {
            DetailsViewModel.ScreenState.Loading -> ScreenProgress()
            is DetailsViewModel.ScreenState.Failure -> ScreenError(
                error = result.error,
                onRetryClicked = onRetryClicked,
                onErrorCloseClicked = onGoBackClicked
            )
            is DetailsViewModel.ScreenState.Success -> ScreenContent(
                events = result.event,
                uiState = uiState,
                onCheckInClicked = onCheckInClicked
            )
        }
    }
}

@Composable
private fun ScreenContent(
    uiState: DetailsViewModel.DetailsScreenUiState,
    events: Post,
    onCheckInClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Size.Size2XSM)
            .verticalScroll(rememberScrollState()),
    ) {
        PostItem(post = events)
        Spacer(modifier = Modifier.weight(Weight1))
        ButtonCheckIn(uiState = uiState, onClickButton = onCheckInClicked)
    }
}

@Composable
private fun PageHeader() {
    Header(
        title = stringResource(id = R.string.posterr),
    )
}

@Composable
private fun PostItem(
    post: Post
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = Size.Size2XSM),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AvatarImage(
            modifier = Modifier.size(Size.Size5XLG),
            imageUrl = post.image,
            initials = EMPTY_STRING
        )
        SpacerHorizontal()
        Text(
            text = post.title,
            modifier = Modifier
                .padding(top = Size.SizeXSM, bottom = Size.SizeSM)
                .fillMaxWidth(),
            style = Font.h2
        )
        Text(
            text = Date(post.date).formatDateAsDayMonthYear(),
            style = Font.body1Bold,
            color = ColorPalette.Support500
        )
        Text(
            text = post.description,
            style = Font.body2,
            color = ColorPalette.Support500
        )
        SpacerVertical()
    }
}

@Composable
private fun ButtonCheckIn(
    uiState: DetailsViewModel.DetailsScreenUiState,
    onClickButton: () -> Unit
) {
    Button(
        onClick = onClickButton,
        text = "Check-In",
        buttonWidth = ButtonWidth.fill(),
        modifier = Modifier.padding(bottom = Size.SizeSM),
        buttonIcon = ButtonIcon.buttonIconText(
            fontIconEnd = stringResource(id = com.francisco.strider.dsc.R.string.icon_long_arrow_right_kit)
        ),
        buttonIconArrangement = Arrangement.SpaceBetween,
        progress = uiState.isLoading.collectAsState().value,
        progressText = stringResource(id = R.string.loading_text)
    )
}


@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen(
        uiState = DetailsViewModel.DetailsScreenUiState(),
        onGoBackClicked = {},
        onCheckInClicked = {},
        onRetryClicked = {}
    )
}