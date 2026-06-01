package by.idt.testapp.domain

interface TableConfig {

    val minRows: Int
        get() = 1

    val maxRows: Int
        get() = 1000

    val minColumns: Int
        get() = 1

    val maxColumns: Int
        get() = 6

    val rowsRange: Array<Int>
    val columnsRange: Array<Int>

    companion object {
        val Default =
            object : TableConfig {
                override val rowsRange: Array<Int> by lazy { arrayOf(minRows, maxRows) }
                override val columnsRange: Array<Int> by lazy { arrayOf(minColumns, maxColumns) }
            }
    }
}
