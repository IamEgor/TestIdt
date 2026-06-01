package by.idt.testapp.domain.model

sealed interface InputValidationResult {
    data class Success(val rows: Int, val cols: Int) : InputValidationResult

    sealed interface Error : InputValidationResult {
        data object InvalidRows : Error

        data object InvalidCols : Error

        data object RowsOutOfRange : Error

        data object ColsOutOfRange : Error
    }
}
