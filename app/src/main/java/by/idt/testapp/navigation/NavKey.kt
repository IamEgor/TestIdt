package by.idt.testapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavKey : androidx.navigation3.runtime.NavKey {
    @Serializable data object Input : NavKey

    @Serializable data class Table(val rows: Int, val cols: Int, val screenId: String) : NavKey
}
