package by.idt.testapp.ui.screen.table

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TableScreenRoute(rows: Int, cols: Int, screenId: String, modifier: Modifier = Modifier) {
    val viewModel: TableViewModel =
        koinViewModel<TableViewModel>(key = screenId, parameters = { parametersOf(rows, cols) })
    val state by viewModel.state.collectAsState()

    TableScreen(
        state = state,
        onCellSingleClick = viewModel::toggleCellColor,
        onCellDoubleClick = viewModel::onCellDoubleClick,
        onDismissEdit = viewModel::onDismissEdit,
        onUpdateCellValue = viewModel::updateCellValue,
        modifier = modifier,
    )
}
