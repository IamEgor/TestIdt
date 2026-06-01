package by.idt.testapp.ui.screen.input

import androidx.lifecycle.ViewModel
import by.idt.testapp.domain.model.InputValidationResult
import by.idt.testapp.domain.usecase.ValidateInputUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

internal class InputViewModel(private val validateInputUseCase: ValidateInputUseCase) :
    ViewModel() {

    val state: StateFlow<InputUiState>
        field = MutableStateFlow(InputUiState())
    private val _effects = Channel<InputEffect>(Channel.BUFFERED)
    val effects: Flow<InputEffect> = _effects.receiveAsFlow()

    fun updateRows(value: String) {
        state.update { it.copy(rows = value.sanitized) }
    }

    fun updateCols(value: String) {
        state.update { it.copy(cols = value.sanitized) }
    }

    private val String.sanitized: String
        get() = filter(Char::isDigit).dropWhile { it == '0' }.ifEmpty { "" }

    fun onGenerate() {
        val rows = state.value.rows
        val cols = state.value.cols
        when (val result = validateInputUseCase(rows, cols)) {
            is InputValidationResult.Error -> {
                state.update { it.copy(validationError = result) }
            }

            is InputValidationResult.Success -> {
                state.update { it.copy(validationError = null) }
                _effects.trySend(InputEffect.NavigateToTable(result.rows, result.cols))
            }
        }
    }
}
