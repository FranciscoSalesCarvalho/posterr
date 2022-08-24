package com.francisco.strider.feature.poster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.francisco.strider.R
import com.francisco.strider.commons.util.keyboard.isKeyboardOpenAsState
import com.francisco.strider.dsc.compose.component.Button
import com.francisco.strider.dsc.compose.component.ButtonIcon
import com.francisco.strider.dsc.compose.component.ButtonStyle
import com.francisco.strider.dsc.compose.component.ButtonWidth
import com.francisco.strider.dsc.compose.component.DefaultTextField
import com.francisco.strider.dsc.compose.component.Header
import com.francisco.strider.dsc.compose.component.ProgressAnimationType
import com.francisco.strider.dsc.compose.component.SpacerVertical
import com.francisco.strider.dsc.compose.component.defaultTextFieldOnValueChange
import com.francisco.strider.dsc.compose.theme.DscTheme
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.dimen.Weight1
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.compose.theme.font.body1Bold
import com.francisco.strider.feature.commons.showScreenErrorDialog
import com.francisco.strider.feature.main.MainActivity
import com.francisco.strider.feature.main.MainViewModel
import com.francisco.strider.feature.poster.PosterViewModel.FieldScreenUiState
import com.francisco.strider.feature.poster.PosterViewModel.ScreenEvent

@Composable
fun PostersScreen(
    viewModel: PosterViewModel,
    flowViewModel: MainViewModel
) {

    val activity = LocalContext.current as MainActivity

    EventConsumer(
        activity = activity,
        viewModel = viewModel,
        flowViewModel = flowViewModel
    )

    Screen(
        uiState = viewModel.uiState,
        onGoBackClicked = viewModel::onGoBackClicked,
        onValueChange = viewModel::onValueChanged,
        onContinueClicked = viewModel::onContinueClicked
    )
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: PosterViewModel,
    flowViewModel: MainViewModel
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is ScreenEvent.GoBack -> activity.onBackPressed()
                is ScreenEvent.SavePostError -> activity.showScreenErrorDialog(error = event.error)
            }
        }
    }
}

@Composable
private fun Screen(
    uiState: FieldScreenUiState,
    onGoBackClicked: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    onContinueClicked: () -> Unit,
) = DscTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.Support100)
            .verticalScroll(rememberScrollState()),
    ) {
        PageHeader(onGoBackClicked = onGoBackClicked)
        Column(modifier = Modifier.padding(all = Size.SizeSM)) {
            TextField(
                uiState = uiState,
                onValueChange = onValueChange
            )
            SpacerVertical(dp = Size.SizeSM)
            MissingCharacters(uiState = uiState)
        }
        Spacer(Modifier.weight(Weight1))
        PosterrButton(
            uiState = uiState,
            onContinueClicked = onContinueClicked
        )
    }
}

@Composable
private fun PageHeader(
    onGoBackClicked: () -> Unit,
) {
    Header(
        title = stringResource(id = R.string.new_posterr),
        fontIconStart = stringResource(id = com.francisco.strider.dsc.R.string.icon_chevron_left_kit),
        fontIconStartClick = onGoBackClicked,
    )
}

@Composable
private fun TextField(
    uiState: FieldScreenUiState,
    onValueChange: (TextFieldValue) -> Unit
) {
    val textFieldValue by uiState.textField.collectAsState()
    val isTextFieldValid by uiState.isTextFieldValid.collectAsState()
    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textFieldValue,
        onValueChange = defaultTextFieldOnValueChange(onValueChange = onValueChange),
        label = stringResource(id = R.string.poster_label),
        error = stringResource(id = R.string.poster_label_field_error).takeIf { !isTextFieldValid },
        errorFontIcon = stringResource(id = com.francisco.strider.dsc.R.string.icon_exclamation_triangle_outline_kit)
    )
}

@Composable
private fun ColumnScope.MissingCharacters(
    uiState: FieldScreenUiState,
) {
    val missingCharacters by uiState.missingCharacters.collectAsState()
    Text(
        modifier = Modifier.align(alignment = Alignment.End),
        text = stringResource(id = R.string.missing_character, missingCharacters)
    )
}

@Composable
private fun PosterrButton(
    onContinueClicked: () -> Unit,
    uiState: FieldScreenUiState
) {
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by isKeyboardOpenAsState()

    val isLoading by uiState.isLoading.collectAsState()

    Button(
        modifier = Modifier.padding(
            bottom = if (isKeyboardOpen) Size.Size0 else Size.SizeSM,
            top = Size.SizeMD,
            start = if (isKeyboardOpen) Size.Size0 else Size.SizeSM,
            end = if (isKeyboardOpen) Size.Size0 else Size.SizeSM
        ),
        text = stringResource(id = R.string.save_posterr),
        onClick = {
            focusManager.clearFocus()
            onContinueClicked()
        },
        textStyle = Font.body1Bold,
        enabled = !isLoading,
        buttonStyle = if (isKeyboardOpen) ButtonStyle.rectangle() else ButtonStyle.primary(),
        buttonWidth = ButtonWidth.fill(),
        buttonIcon = ButtonIcon.buttonIconText(fontIconEnd = stringResource(id = com.francisco.strider.dsc.R.string.icon_long_arrow_right_kit)),
        buttonIconArrangement = Arrangement.SpaceBetween,
        progressText = stringResource(id = R.string.loading_text),
        progress = isLoading,
        progressType = ProgressAnimationType.CIRCLE_SUPPORT100
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen(
        uiState = FieldScreenUiState(),
        onValueChange = {},
        onGoBackClicked = {},
        onContinueClicked = {}
    )
}