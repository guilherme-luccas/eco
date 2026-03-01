package br.com.fiap.eco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import br.com.fiap.eco.navigation.NavigationRoutes
import br.com.fiap.eco.ui.theme.EcoTheme
import br.com.fiap.eco.ui.theme.LocalThemePreference
import br.com.fiap.eco.ui.theme.LocalThemePreferenceUpdater
import br.com.fiap.eco.ui.theme.ThemePreference

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themePreference by rememberSaveable { mutableStateOf(ThemePreference.SYSTEM) }
            val isSystemDark = isSystemInDarkTheme()
            val darkTheme = when (themePreference) {
                ThemePreference.SYSTEM -> isSystemDark
                ThemePreference.LIGHT -> false
                ThemePreference.DARK -> true
            }

            CompositionLocalProvider(
                LocalThemePreference provides themePreference,
                LocalThemePreferenceUpdater provides { newPreference ->
                    themePreference = newPreference
                }
            ) {
                EcoTheme(darkTheme = darkTheme) {
                    NavigationRoutes()
                }
            }
        }
    }
}