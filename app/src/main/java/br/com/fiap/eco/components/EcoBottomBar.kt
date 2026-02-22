package br.com.fiap.eco.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.eco.navigation.Destination

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun EcoBottomBar(navController: NavController, email: String, selectedIndex: Int) {
    val items = listOf(
        BottomNavItem(title = "Início", icon = Icons.Default.Home),
        BottomNavItem(title = "Dicas", icon = Icons.Default.Lightbulb),
        BottomNavItem(title = "Perfil", icon = Icons.Default.Person)
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    when (index) {
                        0 -> navController.navigate(Destination.HomeScreen.createRoute(email)) {
                            popUpTo(Destination.HomeScreen.route) { inclusive = true }
                        }
                        1 -> navController.navigate(Destination.TipsScreen.createRoute(email)) {
                            popUpTo(Destination.HomeScreen.route)
                        }
                        2 -> navController.navigate(Destination.ProfileScreen.createRoute(email)) {
                            popUpTo(Destination.HomeScreen.route)
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}
