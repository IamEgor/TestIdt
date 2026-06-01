package by.idt.testapp.domain.repository

import by.idt.testapp.domain.model.CellData

interface TableRepository {
    fun generateTable(rows: Int, cols: Int): List<List<CellData>>
}
