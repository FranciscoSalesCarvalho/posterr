package com.francisco.strider.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.francisco.strider.dsc.compose.component.Header
import com.francisco.strider.dsc.compose.component.LabelValue
import com.francisco.strider.dsc.compose.component.LabelValueOrientation
import com.francisco.strider.dsc.compose.component.LabelValueText
import com.francisco.strider.dsc.compose.component.SpacerHorizontal
import com.francisco.strider.dsc.compose.component.SpacerVertical
import com.francisco.strider.dsc.compose.theme.DscTheme
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.compose.theme.font.body1Bold
import com.francisco.strider.dsc.extensions.EMPTY_STRING
import com.francisco.strider.feature.commons.ScreenError
import com.francisco.strider.feature.commons.ScreenProgress
import com.francisco.strider.feature.home.HomeViewModel.HomeScreenUiState
import com.francisco.strider.feature.home.HomeViewModel.ScreenEvent
import com.francisco.strider.feature.main.MainActivity
import com.francisco.strider.feature.main.MainViewModel
import java.util.Date

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
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
        onRetryClicked = viewModel::setup,
        onItemClicked = viewModel::onItemClicked
    )
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: HomeViewModel,
    flowViewModel: MainViewModel
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                ScreenEvent.GoBack -> activity.onBackPressed()
                is ScreenEvent.PostSelected -> flowViewModel.itemSelectedId = event.id
                is ScreenEvent.NavigateTo -> flowViewModel.navigate(event.navigation)
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: HomeScreenUiState,
    onGoBackClicked: () -> Unit,
    onRetryClicked: () -> Unit,
    onItemClicked: (id: String) -> Unit
) {
    when (val result = uiState.screenState.collectAsState().value) {
        HomeViewModel.ScreenState.Loading -> ScreenProgress()
        is HomeViewModel.ScreenState.Failure -> ScreenError(
            error = result.error,
            onRetryClicked = onRetryClicked,
            onErrorCloseClicked = onGoBackClicked
        )
        is HomeViewModel.ScreenState.Success -> ScreenContent(
            events = result.events,
            onItemClicked = onItemClicked
        )
    }
}

@Composable
private fun ScreenContent(
    events: List<Post>,
    onItemClicked: (id: String) -> Unit
) = DscTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.Support100),
    ) {
        PageHeader()
        PostList(events = events, onItemClicked = onItemClicked)
    }
}

@Composable
private fun PageHeader() {
    Header(
        title = stringResource(id = R.string.posterr),
    )
}

@Composable
private fun PostList(events: List<Post>, onItemClicked: (id: String) -> Unit) {
    LazyColumn {
        items(items = events) {
            PostItem(post = it, onItemClicked = onItemClicked)
            SpacerVertical(dp = Size.SizeSM)
        }
    }
}

@Composable
private fun PostItem(
    post: Post,
    onItemClicked: (id: String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(all = Size.Size2XSM)
            .clickable {
                onItemClicked(post.id)
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarImage(
            modifier = Modifier.size(Size.Size5XLG),
            imageUrl = post.image,
            initials = EMPTY_STRING
        )
        SpacerHorizontal()
        Column {
            RepositoryInfo(
                label = stringResource(id = R.string.name_repo_label),
                value = post.title,
            )
            RepositoryInfo(
                label = stringResource(id = R.string.date_repo_label),
                value = Date(post.date).formatDateAsDayMonthYear(),
            )
        }
    }
}

@Composable
private fun RepositoryInfo(
    label: String,
    value: String
) {
    LabelValue(
        orientation = LabelValueOrientation.HORIZONTAL,
        spacerBetween = Size.Size2XSM,
        labelText = LabelValueText(
            text = label,
            textStyle = Font.body1Bold
        ),
        valueText = LabelValueText(
            text = value,
            textStyle = Font.body1
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen(
        uiState = HomeScreenUiState(),
        onGoBackClicked = {},
        onRetryClicked = {},
        onItemClicked = {}
    )
}