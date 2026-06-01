package by.idt.testapp.domain.usecase

import by.idt.testapp.domain.TableConfig
import by.idt.testapp.domain.model.CellData
import by.idt.testapp.domain.model.TableGenerationException
import by.idt.testapp.domain.repository.TableRepository

class GenerateTableUseCase(
    private val repository: TableRepository,
    private val tableConfig: TableConfig,
) {
    operator fun invoke(rows: Int, cols: Int): List<List<CellData>> {
        if (
            rows !in tableConfig.minRows..tableConfig.maxRows ||
                cols !in tableConfig.minColumns..tableConfig.maxColumns
        ) {
            throw TableGenerationException()
        }
        return repository.generateTable(rows, cols)
    }
}
