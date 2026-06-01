package by.idt.testapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(primary = Green80, secondary = GreenGrey80, tertiary = LightGreen80)

private val LightColorScheme =
    lightColorScheme(primary = Green40, secondary = GreenGrey40, tertiary = LightGreen40)

@Composable
fun TestIdtTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = typography, content = content)
}
