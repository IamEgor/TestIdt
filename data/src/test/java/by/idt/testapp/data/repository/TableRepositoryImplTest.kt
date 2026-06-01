package by.idt.testapp.data.repository

import by.idt.testapp.domain.repository.TableRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TableRepositoryImplTest {

    private val repository: TableRepository = TableRepositoryImpl()
    private val allowedChars = (('a'..'z') + ('A'..'Z') + ('0'..'9')).toSet()

    @Test
    fun `generates table with correct dimensions`() {
        val result = repository.generateTable(5, 3)

        assertEquals(5, result.size)
        assertTrue(result.all { it.size == 3 })
    }

    @Test
    fun `generates table with minimum size`() {
        val result = repository.generateTable(1, 1)

        assertEquals(1, result.size)
        assertEquals(1, result[0].size)
    }

    @Test
    fun `generates table with maximum size`() {
        val result = repository.generateTable(1000, 6)

        assertEquals(1000, result.size)
        assertTrue(result.all { it.size == 6 })
    }

    @Test
    fun `all cell values are non-empty`() {
        val result = repository.generateTable(10, 4)

        result.forEach { row ->
            row.forEach { cell ->
                assertTrue("Cell value should not be empty", cell.value.isNotEmpty())
            }
        }
    }

    @Test
    fun `all cell values contain only alphanumeric characters`() {
        val result = repository.generateTable(10, 4)

        result.forEach { row ->
            row.forEach { cell ->
                assertTrue(
                    "Cell value '$cell' contains invalid characters",
                    cell.value.all { it in allowedChars },
                )
            }
        }
    }

    @Test
    fun `cell values length is between 5 and 14 characters`() {
        val result = repository.generateTable(10, 4)

        result.forEach { row ->
            row.forEach { cell ->
                assertTrue(
                    "Cell value length ${cell.value.length} should be in 5..14",
                    cell.value.length in 5..14,
                )
            }
        }
    }

    @Test
    fun `multiple calls produce different values`() {
        val table1 = repository.generateTable(10, 4)
        val table2 = repository.generateTable(10, 4)

        val values1 = table1.flatten().map { it.value }.toSet()
        val values2 = table2.flatten().map { it.value }.toSet()

        assertEquals(40, values1.size)
        assertEquals(40, values2.size)
    }

    @Test
    fun `single cell table`() {
        val result = repository.generateTable(1, 1)

        val cell = result[0][0]
        assertTrue(cell.value.isNotEmpty())
        assertTrue(cell.value.length in 5..14)
        assertTrue(cell.value.all { it in allowedChars })
    }
}
