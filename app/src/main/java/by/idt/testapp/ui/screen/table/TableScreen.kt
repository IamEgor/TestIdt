package by.idt.testapp.ui.screen.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import by.idt.testapp.R
import by.idt.testapp.domain.model.CellData
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun TableScreen(
    state: TableUiState,
    onCellSingleClick: (Int, Int) -> Unit,
    onCellDoubleClick: (Int, Int) -> Unit,
    onDismissEdit: () -> Unit,
    onUpdateCellValue: (row: Int, col: Int, newValue: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is TableUiState.Error -> TableScreenContentError(error = state, modifier = modifier)
        TableUiState.Loading -> TableScreenContentLoading(modifier = modifier)
        is TableUiState.Success -> {
            TableScreenContentData(
                cols = state.cols,
                tableData = state.tableData,
                onCellSingleClick = onCellSingleClick,
                onCellDoubleClick = onCellDoubleClick,
                modifier = modifier,
            )
            state.editableCell?.let { (row, col) ->
                val index = row * state.cols + col
                val cell = state.tableData.getOrNull(index) ?: return@let
                TableEditCellDialog(
                    currentValue = cell.value,
                    onDismiss = onDismissEdit,
                    onSave = { newValue -> onUpdateCellValue(row, col, newValue) },
                )
            }
        }
    }
}

@Composable
private fun TableScreenContentError(error: TableUiState.Error, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text =
                when (error) {
                    TableUiState.Error.Generation -> stringResource(R.string.table_generation_error)

                    is TableUiState.Error.General ->
                        stringResource(R.string.table_general_error, error.exception.toString())
                },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun TableScreenContentLoading(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
        Text(
            text = stringResource(R.string.table_loading),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun TableScreenContentData(
    cols: Int,
    tableData: PersistentList<CellData>,
    onCellSingleClick: (Int, Int) -> Unit,
    onCellDoubleClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(cols),
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        itemsIndexed(items = tableData, key = { index, _ -> index }) { index, cell ->
            val row = index / cols
            val col = index % cols
            TableScreenContentCell(
                cell = cell,
                onCellSingleClick = { onCellSingleClick(row, col) },
                onCellDoubleClick = { onCellDoubleClick(row, col) },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TableScreenContentCell(
    cell: CellData,
    onCellSingleClick: () -> Unit,
    onCellDoubleClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier.padding(2.dp)
                .background(
                    if (cell.isHighlighted) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surface
                )
                .border(1.dp, MaterialTheme.colorScheme.outline)
                .combinedClickable(onClick = onCellSingleClick, onDoubleClick = onCellDoubleClick)
                .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = cell.value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color =
                if (cell.isHighlighted) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface,
        )
    }
}
