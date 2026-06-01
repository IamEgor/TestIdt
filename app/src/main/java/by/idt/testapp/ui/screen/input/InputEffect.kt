package by.idt.testapp.ui.screen.input

internal sealed interface InputEffect {
    data class NavigateToTable(val rows: Int, val cols: Int) : InputEffect
}
