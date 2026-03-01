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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.eco.components.EcoBottomBar
import br.com.fiap.eco.components.ImpactSummaryCard
import br.com.fiap.eco.components.ProfileStatisticsCard
import br.com.fiap.eco.repository.ProgressRepository
import br.com.fiap.eco.repository.RoomProgressRepository
import br.com.fiap.eco.repository.RoomUserRepository
import br.com.fiap.eco.repository.UserRepository

@Composable
fun StatsScreen(navController: NavController, email: String) {
    val context = LocalContext.current
    val userRepository: UserRepository = RoomUserRepository(context)
    val progressRepository: ProgressRepository = remember { RoomProgressRepository(context) }
    val user = userRepository.getUserByEmail(email)
    val userId = user?.id ?: 0
    val userStats = if (userId != 0) progressRepository.getUserStats(userId) else null
    val totalImpact = if (userId != 0) progressRepository.getTotalImpact(userId) else null

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
                    text = "Estatísticas",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Acompanhe seu impacto positivo 🌱",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileStatisticsCard(
                    totalPoints = (userStats?.totalPoints ?: 0).toString(),
                    bestStreak = "${userStats?.bestStreak ?: 0} dias"
                )
            }

            item {
                ImpactSummaryCard(
                    title = "Resumo do Impacto Total",
                    co2Avoided = totalImpact?.co2Avoided ?: "0 g",
                    waterSaved = totalImpact?.waterSaved ?: "0 L",
                    plasticAvoided = totalImpact?.plasticAvoided ?: "0 itens"
                )
            }
        }
    }
}

