package br.com.fiap.eco.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EcoColorScheme = lightColorScheme(
    primary = EcoGreen,
    onPrimary = EcoOnPrimary,
    primaryContainer = EcoGreenLight,
    onPrimaryContainer = EcoGreenDark,
    secondary = EcoGreenLight,
    onSecondary = EcoGreenDark,
    tertiary = EcoAccentOrange,
    onTertiary = EcoOnPrimary,
    background = EcoBackground,
    onBackground = EcoOnBackground,
    surface = EcoSurface,
    onSurface = EcoOnSurface,
    surfaceVariant = EcoSurfaceVariant,
    onSurfaceVariant = EcoOnSurfaceVariant,
    error = EcoError,
    errorContainer = EcoErrorContainer,
)

@Composable
fun EcoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EcoColorScheme,
        content = content
    )
}