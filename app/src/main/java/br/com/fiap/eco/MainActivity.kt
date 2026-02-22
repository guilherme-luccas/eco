package br.com.fiap.eco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.fiap.eco.navigation.NavigationRoutes
import br.com.fiap.eco.ui.theme.EcoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoTheme {
                NavigationRoutes()
            }
        }
    }
}