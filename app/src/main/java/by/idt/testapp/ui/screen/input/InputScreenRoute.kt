package by.idt.testapp.ui.screen.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import by.idt.testapp.domain.TableConfig
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun InputScreenRoute(onNavigateToTable: (Int, Int) -> Unit, modifier: Modifier = Modifier) {
    val viewModel: InputViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val tableConfig: TableConfig = koinInject()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is InputEffect.NavigateToTable -> onNavigateToTable(effect.rows, effect.cols)
            }
        }
    }

    InputScreen(
        state = state,
        onRowsChange = viewModel::updateRows,
        onColsChange = viewModel::updateCols,
        onGenerate = viewModel::onGenerate,
        tableConfig = tableConfig,
        modifier = modifier,
    )
}
