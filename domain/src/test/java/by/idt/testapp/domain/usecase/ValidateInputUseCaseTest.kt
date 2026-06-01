package by.idt.testapp.domain.usecase

import by.idt.testapp.domain.TableConfig
import by.idt.testapp.domain.model.InputValidationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateInputUseCaseTest {

    private val useCase = ValidateInputUseCase(TableConfig.Default)

    @Test
    fun `valid inputs return success`() {
        val result = useCase("5", "3")

        assertTrue(result is InputValidationResult.Success)
        assertEquals(5, (result as InputValidationResult.Success).rows)
        assertEquals(3, result.cols)
    }

    @Test
    fun `empty rows returns invalid rows error`() {
        assertTrue(useCase("", "3") is InputValidationResult.Error.InvalidRows)
    }

    @Test
    fun `non numeric rows returns invalid rows error`() {
        assertTrue(useCase("abc", "3") is InputValidationResult.Error.InvalidRows)
    }

    @Test
    fun `empty cols returns invalid cols error`() {
        assertTrue(useCase("5", "") is InputValidationResult.Error.InvalidCols)
    }

    @Test
    fun `non numeric cols returns invalid cols error`() {
        assertTrue(useCase("5", "abc") is InputValidationResult.Error.InvalidCols)
    }

    @Test
    fun `rows below minimum returns out of range error`() {
        assertTrue(useCase("0", "3") is InputValidationResult.Error.RowsOutOfRange)
    }

    @Test
    fun `rows above maximum returns out of range error`() {
        assertTrue(useCase("1001", "3") is InputValidationResult.Error.RowsOutOfRange)
    }

    @Test
    fun `cols below minimum returns out of range error`() {
        assertTrue(useCase("5", "0") is InputValidationResult.Error.ColsOutOfRange)
    }

    @Test
    fun `cols above maximum returns out of range error`() {
        assertTrue(useCase("5", "7") is InputValidationResult.Error.ColsOutOfRange)
    }

    @Test
    fun `minimum boundary values return success`() {
        val result = useCase("1", "1")

        assertTrue(result is InputValidationResult.Success)
        assertEquals(1, (result as InputValidationResult.Success).rows)
        assertEquals(1, result.cols)
    }

    @Test
    fun `maximum boundary values return success`() {
        val result = useCase("1000", "6")

        assertTrue(result is InputValidationResult.Success)
        assertEquals(1000, (result as InputValidationResult.Success).rows)
        assertEquals(6, result.cols)
    }

    @Test
    fun `custom table config boundaries are respected`() {
        val custom = TestTableConfig(2, 5, 3, 4)
        val useCase = ValidateInputUseCase(custom)

        assertTrue(useCase("1", "3") is InputValidationResult.Error.RowsOutOfRange)
        assertTrue(useCase("6", "3") is InputValidationResult.Error.RowsOutOfRange)
        assertTrue(useCase("3", "2") is InputValidationResult.Error.ColsOutOfRange)
        assertTrue(useCase("3", "5") is InputValidationResult.Error.ColsOutOfRange)
        assertTrue(useCase("2", "3") is InputValidationResult.Success)
        assertTrue(useCase("5", "4") is InputValidationResult.Success)
    }

    private class TestTableConfig(
        override val minRows: Int,
        override val maxRows: Int,
        override val minColumns: Int,
        override val maxColumns: Int,
    ) : TableConfig {
        override val rowsRange: Array<Int>
            get() = arrayOf(minRows, maxRows)

        override val columnsRange: Array<Int>
            get() = arrayOf(minColumns, maxColumns)
    }
}
