package by.idt.testapp.data.repository

import by.idt.testapp.domain.model.CellData
import by.idt.testapp.domain.repository.TableRepository
import kotlin.random.Random

class TableRepositoryImpl : TableRepository {

    private val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun generateTable(rows: Int, cols: Int): List<List<CellData>> {
        return List(rows) { List(cols) { CellData(value = randomString()) } }
    }

    private fun randomString(): String {
        val length = Random.nextInt(MIN_LENGTH, MAX_LENGTH)
        return (1..length).map { chars.random() }.joinToString("")
    }

    companion object {
        private const val MIN_LENGTH = 5
        private const val MAX_LENGTH = 15
    }
}
