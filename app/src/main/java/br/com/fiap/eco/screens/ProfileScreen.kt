package br.com.fiap.eco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.eco.components.EcoBottomBar
import br.com.fiap.eco.components.ImpactSummaryCard
import br.com.fiap.eco.components.LogoutButton
import br.com.fiap.eco.components.NotificationSettingRow
import br.com.fiap.eco.components.ProfileStatisticsCard
import br.com.fiap.eco.components.ProfileUserCard
import br.com.fiap.eco.navigation.Destination
import br.com.fiap.eco.repository.RoomUserRepository
import br.com.fiap.eco.repository.UserRepository

@Composable
fun ProfileScreen(navController: NavController, email: String) {
    val userRepository: UserRepository = RoomUserRepository(LocalContext.current)
    val user = userRepository.getUserByEmail(email)
    val userName = user?.name ?: "Usuário"

    val notificationsEnabled = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            EcoBottomBar(navController, email, selectedIndex = 2)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Perfil",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Seu impacto positivo 🌍",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileUserCard(userName = userName)
            }

            item {
                NotificationSettingRow(
                    checked = notificationsEnabled.value,
                    onCheckedChange = { notificationsEnabled.value = it }
                )
            }

            item {
                ProfileStatisticsCard(
                    totalPoints = "420",
                    bestStreak = "14 dias"
                )
            }

            item {
                ImpactSummaryCard()
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                LogoutButton(
                    onClick = {
                        navController.navigate(Destination.LoginScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
