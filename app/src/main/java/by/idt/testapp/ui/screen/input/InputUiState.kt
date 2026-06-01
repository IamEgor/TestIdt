package by.idt.testapp.ui.screen.input

import by.idt.testapp.domain.model.InputValidationResult

internal data class InputUiState(
    val rows: String = "",
    val cols: String = "",
    val validationError: InputValidationResult.Error? = null,
)
