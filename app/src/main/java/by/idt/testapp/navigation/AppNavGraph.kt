package by.idt.testapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import by.idt.testapp.ui.screen.input.InputScreenRoute
import by.idt.testapp.ui.screen.table.TableScreenRoute
import kotlin.uuid.Uuid

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(NavKey.Input)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = { key ->
            when (val navKey = key as NavKey) {
                is NavKey.Input ->
                    NavEntry(navKey) {
                        InputScreenRoute(
                            onNavigateToTable = { rows, cols ->
                                backStack.add(NavKey.Table(rows, cols, Uuid.random().toString()))
                            }
                        )
                    }

                is NavKey.Table ->
                    NavEntry(navKey) {
                        TableScreenRoute(
                            rows = navKey.rows,
                            cols = navKey.cols,
                            screenId = navKey.screenId,
                        )
                    }
            }
        },
    )
}
