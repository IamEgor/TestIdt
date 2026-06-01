package by.idt.testapp.ui.screen.input

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import by.idt.testapp.R
import by.idt.testapp.domain.TableConfig
import by.idt.testapp.domain.model.InputValidationResult

private val InputMaxWidth = 400.dp

@Composable
internal fun InputScreen(
    state: InputUiState,
    onRowsChange: (String) -> Unit,
    onColsChange: (String) -> Unit,
    onGenerate: () -> Unit,
    tableConfig: TableConfig,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = stringResource(R.string.input_title),
            style = MaterialTheme.typography.headlineMedium,
        )
        InputTextField(
            value = state.rows,
            label = stringResource(R.string.input_rows_label, *tableConfig.rowsRange),
            onValueChange = onRowsChange,
        )
        InputTextField(
            value = state.cols,
            label = stringResource(R.string.input_cols_label, *tableConfig.columnsRange),
            onValueChange = onColsChange,
        )
        if (state.validationError != null) {
            val resources = LocalResources.current
            Text(
                text =
                    state.validationError.getMessageText(
                        resources = resources,
                        tableConfig = tableConfig,
                    ),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Button(
            onClick = onGenerate,
            enabled = state.rows.isNotBlank() && state.cols.isNotBlank(),
            modifier = Modifier.widthIn(max = InputMaxWidth).fillMaxWidth(),
        ) {
            Text(stringResource(R.string.input_generate))
        }
    }
}

@Composable
private fun InputTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.widthIn(max = InputMaxWidth).fillMaxWidth(),
    )
}

private fun InputValidationResult.Error.getMessageText(
    resources: Resources,
    tableConfig: TableConfig,
): String {
    val resId =
        when (this) {
            InputValidationResult.Error.InvalidRows -> R.string.error_invalid_rows
            InputValidationResult.Error.InvalidCols -> R.string.error_invalid_cols
            InputValidationResult.Error.RowsOutOfRange -> R.string.error_rows_range
            InputValidationResult.Error.ColsOutOfRange -> R.string.error_cols_range
        }
    val formatArgs: Array<Int> =
        when (this) {
            InputValidationResult.Error.RowsOutOfRange -> tableConfig.rowsRange
            InputValidationResult.Error.ColsOutOfRange -> tableConfig.columnsRange
            else -> emptyArray()
        }
    return resources.getString(resId, *formatArgs)
}
