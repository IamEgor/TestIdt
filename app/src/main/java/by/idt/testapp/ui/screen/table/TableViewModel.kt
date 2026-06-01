package by.idt.testapp.ui.screen.table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.idt.testapp.domain.model.TableGenerationException
import by.idt.testapp.domain.usecase.GenerateTableUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class TableViewModel(rows: Int, cols: Int, generateTableUseCase: GenerateTableUseCase) :
    ViewModel() {

    val state: StateFlow<TableUiState>
        field = MutableStateFlow<TableUiState>(TableUiState.Loading)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val data = generateTableUseCase(rows, cols).flatten().toPersistentList()
                state.value = TableUiState.Success(cols = cols, tableData = data)
            } catch (_: TableGenerationException) {
                state.value = TableUiState.Error.Generation
            } catch (e: Exception) {
                state.value = TableUiState.Error.General(e)
            }
        }
    }

    fun toggleCellColor(row: Int, col: Int) {
        state.update { old ->
            if (old is TableUiState.Success) {
                val index = row * old.cols + col
                old.copy(
                    tableData =
                        old.tableData.set(
                            index,
                            old.tableData[index].copy(
                                isHighlighted = !old.tableData[index].isHighlighted
                            ),
                        )
                )
            } else old
        }
    }

    fun onCellDoubleClick(row: Int, col: Int) {
        state.update { old ->
            if (old is TableUiState.Success) {
                old.copy(editableCell = EditableCell(row, col))
            } else old
        }
    }

    fun onDismissEdit() {
        state.update { old ->
            if (old is TableUiState.Success) old.copy(editableCell = null) else old
        }
    }

    fun updateCellValue(row: Int, col: Int, newValue: String) {
        state.update { old ->
            if (old is TableUiState.Success) {
                val index = row * old.cols + col
                old.copy(
                    tableData =
                        old.tableData.set(index, old.tableData[index].copy(value = newValue)),
                    editableCell = null,
                )
            } else old
        }
    }
}
