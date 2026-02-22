package br.com.fiap.eco.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.eco.repository.RoomUserRepository
import br.com.fiap.eco.repository.UserRepository
import br.com.fiap.eco.repository.getAirQuality
import br.com.fiap.eco.repository.getCoordinates
import br.com.fiap.eco.repository.getCurrentWeather

data class EcoTip(
    val emoji: String,
    val title: String,
    val description: String
)

@Composable
fun TipsScreen(navController: NavController, email: String) {
    val userRepository: UserRepository = RoomUserRepository(LocalContext.current)
    val user = userRepository.getUserByEmail(email)
    val city = user?.city ?: "São Paulo"

    // Coordenadas da cidade
    val geo = getCoordinates(city)
    val lat = geo?.lat ?: -23.55
    val lon = geo?.lon ?: -46.63

    // Clima e qualidade do ar
    val weather = getCurrentWeather(lat, lon)
    val airQuality = getAirQuality(lat, lon)

    // Cria dicas baseado na resposta da api
    val aqi = airQuality?.list?.firstOrNull()?.main?.aqi
    val weatherId = weather?.current?.weather?.firstOrNull()?.id
    val weatherMain = weather?.current?.weather?.firstOrNull()?.main

    val dynamicTip = when {
        aqi != null && aqi >= 4 -> EcoTip(
            emoji = "😷",
            title = "Poluição Alta",
            description = "A qualidade do ar está ruim hoje. Use máscara ao sair e evite exercícios ao ar livre!"
        )
        aqi != null && aqi == 3 -> EcoTip(
            emoji = "⚠️",
            title = "Ar Moderado",
            description = "A qualidade do ar está moderada. Prefira atividades em ambientes fechados."
        )
        weatherId != null && weatherId in 200..531 -> EcoTip(
            emoji = "🌧️",
            title = "Dia Chuvoso",
            description = "Aproveite a chuva para coletar água! Use baldes e recipientes para reutilizar."
        )
        weatherMain == "Clear" -> EcoTip(
            emoji = "☀️",
            title = "Dia Ensolarado",
            description = "Lembre-se de usar protetor solar e manter-se hidratado ao sair!"
        )
        weatherMain == "Clouds" -> EcoTip(
            emoji = "☁️",
            title = "Dia Nublado",
            description = "Ótimo dia para caminhar ou andar de bike ao invés de usar o carro!"
        )
        else -> EcoTip(
            emoji = "🌍",
            title = "Dica do Dia",
            description = "Cada pequena ação conta! Comece reciclando algo hoje."
        )
    }

    val staticTips = listOf(
        EcoTip(
            emoji = "💧",
            title = "Você sabia?",
            description = "Banhos mais curtos podem economizar até 570 litros de água por mês."
        ),
        EcoTip(
            emoji = "♻️",
            title = "Dica de Reciclagem",
            description = "Reciclar uma lata de alumínio economiza energia suficiente para uma TV funcionar por 3 horas."
        ),
        EcoTip(
            emoji = "🛍️",
            title = "Compras Conscientes",
            description = "Usar sacolas reutilizáveis evita que mais de 1.000 sacolas plásticas entrem em aterros por ano."
        ),
        EcoTip(
            emoji = "🚲",
            title = "Mobilidade Verde",
            description = "Ir de bicicleta ao trabalho reduz sua pegada de carbono em até 67% comparado ao carro."
        )
    )

    Scaffold(
        bottomBar = {
            EcoBottomBar(navController, email, selectedIndex = 1)
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
                    text = "Dicas Eco",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Aprenda e inspire-se! 💡",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Dicas",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Dicas Personalizadas",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Baseadas no clima e ar da sua região",
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dynamicTip.emoji,
                                    fontSize = 24.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = dynamicTip.title,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = dynamicTip.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text(
                                text = "🌐 Baseado em dados da API",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            items(staticTips.size) { index ->
                val tip = staticTips[index]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tip.emoji,
                                fontSize = 24.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tip.title,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tip.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
