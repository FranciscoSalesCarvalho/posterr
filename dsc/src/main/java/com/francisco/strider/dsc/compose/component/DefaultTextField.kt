package com.francisco.strider.dsc.compose.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.francisco.strider.dsc.R
import com.francisco.strider.dsc.compose.theme.color.ColorPalette
import com.francisco.strider.dsc.compose.theme.dimen.Size
import com.francisco.strider.dsc.compose.theme.font.Font
import com.francisco.strider.dsc.extensions.isMaskSign

@Composable
fun DefaultTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    labelFocused: String = label,
    fontIconEnd: String? = null,
    enabled: Boolean = true,
    textStyle: TextStyle = Font.body1,
    error: String? = null,
    errorFontIcon: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = defaultTextFieldColors(),
    testTag: String = DefaultTextFieldTestTag.TEXT_FIELD
) {
    val focusState = interactionSource.collectIsFocusedAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            value = value,
            enabled = enabled,
            onValueChange = onValueChange,
            textStyle = textStyle,
            label = defaultTextFieldLabel(
                label = label,
                labelFocused = labelFocused,
                currentText = value.text,
                isFocus = focusState.value
            ),
            trailingIcon = fontIconEnd?.let { defaultTextFieldIcon(fontIcon = it) },
            interactionSource = interactionSource,
            isError = error != null,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            colors = colors
        )
        DefaultTextFieldError(
            error = error,
            errorFontIcon = errorFontIcon
        )
    }
}

@Composable
fun DefaultTextFieldError(
    error: String? = null,
    errorFontIcon: String? = null
) {
    if (error != null && errorFontIcon != null) {
        IconLabelValue(
            modifier = Modifier.padding(Size.SizeXSM),
            labelText = LabelValueText(
                text = error,
                textStyle = Font.caption,
                color = ColorPalette.Critical300
            ),
            fontIcon = errorFontIcon,
            iconSize = Size.SizeSM,
            iconColor = ColorPalette.Critical300
        )
    }
}

fun defaultTextFieldLabel(
    label: String,
    labelFocused: String,
    currentText: String,
    isFocus: Boolean
): @Composable () -> Unit = {
    Text(
        text = if (isFocus || currentText.isNotEmpty()) labelFocused else label,
        style = if (isFocus || currentText.isNotEmpty()) Font.caption else Font.body1,
        color = ColorPalette.Support400
    )
}

fun defaultTextFieldIcon(
    fontIcon: String
): @Composable () -> Unit = {
    IconText(
        fontIcon = fontIcon,
        iconSize = Size.SizeMD,
        color = ColorPalette.Primary200
    )
}

@Composable
fun defaultTextFieldColors(
    backgroundColor: Color = ColorPalette.Transparent,
    textColor: Color = ColorPalette.Support500,
    disabledTextColor: Color = ColorPalette.Support500,
    focusedIndicatorColor: Color = ColorPalette.Primary200,
    unfocusedIndicatorColor: Color = ColorPalette.Support400,
    disabledIndicatorColor: Color = ColorPalette.Support400,
    focusedLabelColor: Color = ColorPalette.Support400,
    unfocusedLabelColor: Color = ColorPalette.Support400,
    cursorColor: Color = ColorPalette.Primary200,
    errorCursorColor: Color = ColorPalette.Primary200,
    errorIndicatorColor: Color = ColorPalette.Critical300,
    errorLabelColor: Color = ColorPalette.Critical300,
    errorTrailingIconColor: Color = ColorPalette.Primary200
) = TextFieldDefaults.textFieldColors(
    backgroundColor = backgroundColor,
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    focusedIndicatorColor = focusedIndicatorColor,
    unfocusedIndicatorColor = unfocusedIndicatorColor,
    disabledIndicatorColor = disabledIndicatorColor,
    focusedLabelColor = focusedLabelColor,
    unfocusedLabelColor = unfocusedLabelColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor,
    errorIndicatorColor = errorIndicatorColor,
    errorLabelColor = errorLabelColor,
    errorTrailingIconColor = errorTrailingIconColor
)

fun defaultTextFieldOnValueChange(
    maxLength: Int = Int.MAX_VALUE,
    onValueChange: (TextFieldValue) -> Unit
): (TextFieldValue) -> Unit = {
    if (it.text.length <= maxLength) {
        onValueChange(it)
    }
}

@Deprecated("Use NumberMaskVisualTransformation instead")
fun maskTextFieldOnValueChange(
    currentText: String, //Required to verify if char added or removed
    maxLength: Int,
    applyMask: (String) -> String,
    onValueChange: (TextFieldValue) -> Unit
) = defaultTextFieldOnValueChange(maxLength) {
    val isAdd = it.text.length > currentText.length
    val currentSelection = it.selection.end
    val masked = applyMask.invoke(it.text)
    val isSign = masked.isMaskSign(currentSelection.dec())
    val isSignAhead = masked.isMaskSign(currentSelection)
    val newSelection = when {
        isAdd && isSign && isSignAhead -> currentSelection.inc().inc()
        isAdd && isSign -> currentSelection.inc()
        else -> currentSelection
    }
    val textFieldValue = it.copy(
        text = masked,
        selection = TextRange(newSelection)
    )
    onValueChange(textFieldValue)
}

object DefaultTextFieldTestTag {
    const val TEXT_FIELD = "DefaultTextField-TextField"
    const val SEARCH_TEXT_FIELD = "SearchTextField-SearchTextField"
    const val TEXT_DESCRIPTION = "DefaultTextField-TextDescription"
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun DefaultTextFieldPreview() {
    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = TextFieldValue(text = ""),
        onValueChange = {},
        label = "Label",
        fontIconEnd = stringResource(id = R.string.icon_calendar_day_kit)
    )
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun DefaultTextFieldWithTextPreview() {
    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = TextFieldValue(text = "Placeholder text"),
        onValueChange = {},
        label = "Label",
        fontIconEnd = stringResource(id = R.string.icon_calendar_day_kit)
    )
}

@Preview(showBackground = true, heightDp = 100)
@Composable
private fun DefaultTextFieldWithErrorPreview() {
    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = TextFieldValue(text = ""),
        onValueChange = {},
        label = "Label",
        fontIconEnd = stringResource(id = R.string.icon_calendar_day_kit),
        error = "Placeholder error",
        errorFontIcon = stringResource(id = R.string.icon_exclamation_triangle_outline_kit)
    )
}