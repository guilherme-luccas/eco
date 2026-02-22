package br.com.fiap.eco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.eco.components.AirQualityCard
import br.com.fiap.eco.components.DailyGoalCard
import br.com.fiap.eco.components.EcoBottomBar
import br.com.fiap.eco.components.HabitItemCard
import br.com.fiap.eco.components.WeatherCard
import br.com.fiap.eco.model.EcoHabit
import br.com.fiap.eco.repository.RoomUserRepository
import br.com.fiap.eco.repository.UserRepository
import br.com.fiap.eco.repository.getAirQuality
import br.com.fiap.eco.repository.getCoordinates
import br.com.fiap.eco.repository.getCurrentWeather

@Composable
fun HomeScreen(navController: NavController, email: String) {
    val userRepository: UserRepository = RoomUserRepository(LocalContext.current)
    val user = userRepository.getUserByEmail(email)
    val city = user?.city ?: "São Paulo"
    val userName = user?.name ?: "Usuário"

    val geo = getCoordinates(city)
    val lat = geo?.lat ?: -23.55
    val lon = geo?.lon ?: -46.63

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
                .background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Olá! 👋 $userName",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Que hábitos você praticou hoje?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AirQualityCard(airQuality, modifier = Modifier.weight(1f))
                    WeatherCard(weather, city, modifier = Modifier.weight(1f))
                }
            }

            item {
                DailyGoalCard(
                    completedCount = completedCount,
                    totalHabits = habits.size,
                    totalPoints = totalPoints
                )
            }

            itemsIndexed(habits) { index, habit ->
                val isChecked = checkedHabits.contains(index)
                HabitItemCard(
                    habit = habit,
                    isChecked = isChecked,
                    onCheckedChange = { checked ->
                        if (checked) checkedHabits.add(index)
                        else checkedHabits.remove(index)
                    }
                )
            }
        }
    }
}
