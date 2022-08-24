package com.francisco.strider.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.francisco.strider.R
import com.francisco.strider.commons.util.compose.GetLifeCycleEvent
import com.francisco.strider.data.entities.Post
import com.francisco.strider.dsc.compose.component.Header
import com.francisco.strider.dsc.compose.component.SpacerVertical
import com.francisco.strider.dsc.compose.theme.DscTheme
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Line
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.compose.theme.font.body1Bold
import com.francisco.strider.feature.commons.ScreenError
import com.francisco.strider.feature.commons.ScreenProgress
import com.francisco.strider.feature.home.HomeViewModel.HomeScreenUiState
import com.francisco.strider.feature.home.HomeViewModel.ScreenEvent
import com.francisco.strider.feature.main.MainActivity
import com.francisco.strider.feature.main.MainViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    flowViewModel: MainViewModel
) {

    val activity = LocalContext.current as MainActivity

    GetLifeCycleEvent(
        lifecycleEvent = Lifecycle.Event.ON_RESUME,
        onEventCalled = viewModel::setup
    )

    EventConsumer(
        activity = activity,
        viewModel = viewModel,
        flowViewModel = flowViewModel
    )

    Screen(
        uiState = viewModel.uiState,
        onNewPostClicked = viewModel::onNewPostClicked,
        onUserClicked = viewModel::onUserClicked,
        onGoBackClicked = viewModel::onGoBackClicked,
        onRetryClicked = viewModel::setup
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
                is ScreenEvent.NavigateTo -> flowViewModel.navigate(event.navigation)
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: HomeScreenUiState,
    onNewPostClicked: () -> Unit,
    onUserClicked: () -> Unit,
    onGoBackClicked: () -> Unit,
    onRetryClicked: () -> Unit,
) {
    when (val result = uiState.screenState.collectAsState().value) {
        HomeViewModel.ScreenState.Loading -> ScreenProgress()
        is HomeViewModel.ScreenState.Failure -> ScreenError(
            error = result.error,
            onRetryClicked = onRetryClicked,
            onErrorCloseClicked = onGoBackClicked
        )
        is HomeViewModel.ScreenState.Success -> ScreenContent(
            posts = result.posts,
            onNewPostClicked = onNewPostClicked,
            onUserClicked = onUserClicked
        )
    }
}

@Composable
private fun ScreenContent(
    posts: List<Post>,
    onNewPostClicked: () -> Unit,
    onUserClicked: () -> Unit,
) = DscTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.Support100),
    ) {
        PageHeader(
            onNewPostClicked = onNewPostClicked,
            onUserClicked = onUserClicked
        )
        if (posts.isEmpty()) {
            PostsEmpty()
        } else {
            Column(modifier = Modifier.padding(all = Size.SizeSM)) {
                PostList(posts = posts)
            }
        }
    }
}

@Composable
private fun PageHeader(
    onNewPostClicked: () -> Unit,
    onUserClicked: () -> Unit,
) {
    Header(
        title = stringResource(id = R.string.posterr),
        fontIconStart = stringResource(id = com.francisco.strider.dsc.R.string.icon_user_kit),
        fontIconStartClick = onUserClicked,
        fontIconEnd = stringResource(id = com.francisco.strider.dsc.R.string.icon_pencil_kit),
        fontIconEndClick = onNewPostClicked
    )
}

@Composable
private fun PostList(posts: List<Post>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(posts) {
            PostItem(post = it)
            SpacerVertical(dp = Size.SizeSM)
        }
    }
}

@Composable
private fun PostItem(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(width = Line.LineSM, color = ColorPalette.Support500),
        backgroundColor = ColorPalette.Support200,
    ) {
        Column(modifier = Modifier.padding(all = Size.Size2XSM)) {
            Text(
                text = post.message,
                style = Font.body1Bold,
                color = ColorPalette.Support500
            )
            SpacerVertical()
            Divider()
        }
    }
}

@Composable
private fun PostsEmpty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Size.SizeSM),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.posts_empty),
            textAlign = TextAlign.Center,
            style = Font.body1Bold,
            color = ColorPalette.Support400
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen(
        uiState = HomeScreenUiState(),
        onNewPostClicked = {},
        onUserClicked = {},
        onGoBackClicked = {},
        onRetryClicked = {}
    )
}