package by.idt.testapp.domain.usecase

import by.idt.testapp.domain.TableConfig
import by.idt.testapp.domain.model.CellData
import by.idt.testapp.domain.model.TableGenerationException
import by.idt.testapp.domain.repository.TableRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GenerateTableUseCaseTest {

    private val fakeRepository =
        object : TableRepository {
            override fun generateTable(rows: Int, cols: Int) =
                List(rows) { r -> List(cols) { c -> CellData(value = "$r-$c") } }
        }

    private val useCase = GenerateTableUseCase(fakeRepository, TableConfig.Default)

    @Test
    fun `generates table with correct dimensions`() {
        val result = useCase(5, 3)

        assertEquals(5, result.size)
        assertTrue(result.all { it.size == 3 })
    }

    @Test
    fun `cell values match row and column indices`() {
        val result = useCase(2, 2)

        assertEquals("0-0", result[0][0].value)
        assertEquals("0-1", result[0][1].value)
        assertEquals("1-0", result[1][0].value)
        assertEquals("1-1", result[1][1].value)
    }

    @Test(expected = TableGenerationException::class)
    fun `rows below minimum throws exception`() {
        useCase(0, 3)
    }

    @Test(expected = TableGenerationException::class)
    fun `rows above maximum throws exception`() {
        useCase(1001, 3)
    }

    @Test(expected = TableGenerationException::class)
    fun `cols below minimum throws exception`() {
        useCase(5, 0)
    }

    @Test(expected = TableGenerationException::class)
    fun `cols above maximum throws exception`() {
        useCase(5, 7)
    }

    @Test
    fun `minimum boundary values work`() {
        val result = useCase(1, 1)

        assertEquals(1, result.size)
        assertEquals(1, result[0].size)
    }

    @Test
    fun `maximum boundary values work`() {
        val result = useCase(1000, 6)

        assertEquals(1000, result.size)
        assertTrue(result.all { it.size == 6 })
    }
}
