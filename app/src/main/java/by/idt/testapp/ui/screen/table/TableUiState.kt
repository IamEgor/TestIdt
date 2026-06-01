package by.idt.testapp.ui.screen.table

import by.idt.testapp.domain.model.CellData
import kotlinx.collections.immutable.PersistentList

internal sealed interface TableUiState {
    data object Loading : TableUiState

    data class Success(
        val cols: Int,
        val tableData: PersistentList<CellData>,
        val editableCell: EditableCell? = null,
    ) : TableUiState

    sealed interface Error : TableUiState {
        data object Generation : Error

        data class General(val exception: Exception) : Error
    }
}

internal data class EditableCell(val row: Int, val col: Int)
