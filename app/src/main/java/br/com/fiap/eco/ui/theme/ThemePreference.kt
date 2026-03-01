package br.com.fiap.eco.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

enum class ThemePreference {
    SYSTEM,
    LIGHT,
    DARK
}

val LocalThemePreference = staticCompositionLocalOf { ThemePreference.SYSTEM }

val LocalThemePreferenceUpdater = staticCompositionLocalOf<(ThemePreference) -> Unit> { {} }

