package br.com.fiap.eco.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EcoLightColorScheme = lightColorScheme(
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

private val EcoDarkColorScheme = darkColorScheme(
    primary = EcoDarkPrimary,
    onPrimary = EcoDarkOnPrimary,
    primaryContainer = EcoDarkPrimaryContainer,
    onPrimaryContainer = EcoDarkOnPrimaryContainer,
    secondary = EcoDarkSecondary,
    onSecondary = EcoDarkOnSecondary,
    tertiary = EcoDarkTertiary,
    onTertiary = EcoDarkOnTertiary,
    background = EcoDarkBackground,
    onBackground = EcoDarkOnBackground,
    surface = EcoDarkSurface,
    onSurface = EcoDarkOnSurface,
    surfaceVariant = EcoDarkSurfaceVariant,
    onSurfaceVariant = EcoDarkOnSurfaceVariant,
    error = EcoDarkError,
    errorContainer = EcoDarkErrorContainer,
)

@Composable
fun EcoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) EcoDarkColorScheme else EcoLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}