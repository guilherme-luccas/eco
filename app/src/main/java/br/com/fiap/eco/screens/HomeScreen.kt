package br.com.fiap.eco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.eco.navigation.Destination
import br.com.fiap.eco.repository.RoomUserRepository
import br.com.fiap.eco.repository.UserRepository
import br.com.fiap.eco.repository.getAirQuality
import br.com.fiap.eco.repository.getCoordinates
import br.com.fiap.eco.repository.getCurrentWeather

data class EcoHabit(
    val emoji: String,
    val name: String,
    val points: Int
)

@Composable
fun HomeScreen(navController: NavController, email: String) {
    val userRepository: UserRepository = RoomUserRepository(LocalContext.current)
    val user = userRepository.getUserByEmail(email)
    val city = user?.city ?: "São Paulo"
    val userName = user?.name ?: "Usuário"

    // Coordenadas da cidade
    val geo = getCoordinates(city)
    val lat = geo?.lat ?: -23.55
    val lon = geo?.lon ?: -46.63

    // Clima e qualidade do ar
    val weather = getCurrentWeather(lat, lon)
    val airQuality = getAirQuality(lat, lon)

    val habits = remember {
        listOf(
            EcoHabit("♻️", "Reciclei plástico", 10),
            EcoHabit("🛍️", "Usei sacola reutilizável", 15),
            EcoHabit("💧", "Economizei água", 20),
            EcoHabit("🚶", "Fui a pé ou de bike", 25),
            EcoHabit("🌿", "Evitei descartáveis", 15)
        )
    }

    val checkedHabits = remember { mutableStateListOf<Int>() }
    val completedCount = checkedHabits.size
    val totalPoints = checkedHabits.sumOf { habits[it].points }

    Scaffold(
        bottomBar = {
            EcoBottomBar(navController, email, selectedIndex = 0)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Olá! 👋 ${userName}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Que hábitos você praticou hoje?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Air Quality + Weather cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Card da qualidade do AR
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Default.Air,
                                contentDescription = "Ar",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Qualidade do Ar",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                            val aqiText = when (airQuality?.list?.firstOrNull()?.main?.aqi) {
                                1 -> "Bom"
                                2 -> "Razoável"
                                3 -> "Moderado"
                                4 -> "Ruim"
                                5 -> "Muito Ruim"
                                else -> "Carregando..."
                            }
                            Text(
                                text = aqiText,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            val pm25 = airQuality?.list?.firstOrNull()?.components?.pm25
                            Text(
                                text = if (pm25 != null) "PM2.5: ${String.format("%.0f", pm25)} µg/m³" else "",
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }

                    // Card do clima
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = "Clima",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Clima em $city",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = MaterialTheme.typography.labelSmall
                            )
                            val temp = weather?.current?.temp
                            Text(
                                text = if (temp != null) "${temp.toInt()}°C" else "...",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            val description = weather?.current?.weather?.firstOrNull()?.description
                                ?.replaceFirstChar { it.uppercase() } ?: ""
                            Text(
                                text = description,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            // Card objetivo diario
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "🎯 Meta Diária",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "$completedCount",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 36.sp
                                )
                                Text(
                                    text = "/${habits.size}",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                            Text(
                                text = "hábitos concluídos",
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$totalPoints",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp
                            )
                            Text(
                                text = "pontos",
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            // Lista de habito
            items(habits.size) { index ->
                val habit = habits[index]
                val isChecked = checkedHabits.contains(index)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isChecked)
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (isChecked) checkedHabits.remove(index)
                                else checkedHabits.add(index)
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isChecked) Icons.Default.CheckCircle
                                else Icons.Default.RadioButtonUnchecked,
                                contentDescription = "",
                                tint = if (isChecked) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = habit.emoji,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = habit.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text(
                                text = "+${habit.points}",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

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
