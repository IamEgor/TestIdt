package by.idt.testapp.domain.usecase

import by.idt.testapp.domain.TableConfig
import by.idt.testapp.domain.model.InputValidationResult

class ValidateInputUseCase(private val tableConfig: TableConfig) {
    operator fun invoke(rows: String, cols: String): InputValidationResult {
        val r = rows.toIntOrNull() ?: return InputValidationResult.Error.InvalidRows
        val c = cols.toIntOrNull() ?: return InputValidationResult.Error.InvalidCols
        if (r !in tableConfig.minRows..tableConfig.maxRows)
            return InputValidationResult.Error.RowsOutOfRange
        if (c !in tableConfig.minColumns..tableConfig.maxColumns)
            return InputValidationResult.Error.ColsOutOfRange
        return InputValidationResult.Success(r, c)
    }
}
