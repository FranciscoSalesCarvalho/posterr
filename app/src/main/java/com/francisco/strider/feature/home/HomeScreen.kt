package com.francisco.strider.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.francisco.strider.R
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
import com.francisco.strider.feature.home.HomeViewModel.ScreenEvent
import com.francisco.strider.feature.main.MainActivity
import com.francisco.strider.feature.main.MainViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

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
    uiState: MutableStateFlow<Flow<PagingData<Post>>>,
    onGoBackClicked: () -> Unit,
    onRetryClicked: () -> Unit,
) {
    val uiPagingState by uiState.collectAsState()
    when (val result = uiPagingState.collectAsLazyPagingItems().loadState.refresh) {
        LoadState.Loading -> ScreenProgress()
        is LoadState.Error -> ScreenError(
            error = result.error,
            onRetryClicked = onRetryClicked,
            onErrorCloseClicked = onGoBackClicked
        )
        is LoadState.NotLoading -> ScreenContent(posts = uiPagingState)
    }
}

@Composable
private fun ScreenContent(
    posts: Flow<PagingData<Post>>
) = DscTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.Support100),
    ) {
        PageHeader()
        PostList(posts = posts)
    }
}

@Composable
private fun PageHeader() {
    Header(
        title = stringResource(id = R.string.posterr),
    )
}

@Composable
private fun PostList(posts: Flow<PagingData<Post>>) {
    val lazyPosts: LazyPagingItems<Post> = posts.collectAsLazyPagingItems()
    LazyColumn {
        items(items = lazyPosts) { post ->
            post?.let {
                PostItem(post = it)
                SpacerVertical(dp = Size.SizeSM)
            }
        }
    }
}

@Composable
private fun PostItem(post: Post) {
    Row(
        modifier = Modifier.padding(all = Size.Size2XSM),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarImage(
            modifier = Modifier.size(Size.Size5XLG),
            imageUrl = post.imageUrl,
            initials = EMPTY_STRING
        )
        SpacerHorizontal()
        Column {
            RepositoryInfo(
                label = stringResource(id = R.string.name_repo_label),
                value = post.name,
            )
            RepositoryInfo(
                label = stringResource(id = R.string.author_repo_label),
                value = post.authorName,
            )
            RepositoryInfo(
                label = stringResource(id = R.string.start_repo_label),
                value = post.starQuantity.toString(),
            )
            RepositoryInfo(
                label = stringResource(id = R.string.fork_repo_label),
                value = post.forkQuantity.toString(),
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
        uiState = MutableStateFlow(value = flowOf()),
        onGoBackClicked = {}
    ) {}
}