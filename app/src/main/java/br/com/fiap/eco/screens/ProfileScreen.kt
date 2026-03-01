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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.eco.components.EcoBottomBar
import br.com.fiap.eco.components.LogoutButton
import br.com.fiap.eco.components.NotificationSettingRow
import br.com.fiap.eco.components.ProfileUserCard
import br.com.fiap.eco.navigation.Destination
import br.com.fiap.eco.repository.RoomUserRepository
import br.com.fiap.eco.repository.UserRepository
import br.com.fiap.eco.ui.theme.LocalThemePreference
import br.com.fiap.eco.ui.theme.LocalThemePreferenceUpdater
import br.com.fiap.eco.ui.theme.ThemePreference

@Composable
fun ProfileScreen(navController: NavController, email: String) {
    val context = LocalContext.current
    val userRepository: UserRepository = RoomUserRepository(context)
    val user = userRepository.getUserByEmail(email)
    val userId = user?.id ?: 0
    val userName = user?.name ?: "Usuário"

    val notificationsEnabled = remember { mutableStateOf(true) }

    val themePreference = LocalThemePreference.current
    val setThemePreference = LocalThemePreferenceUpdater.current

    Scaffold(
        bottomBar = {
            EcoBottomBar(navController, email, selectedIndex = 3)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 96.dp),
                contentPadding = PaddingValues(top = 16.dp),
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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Tema do app",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Escolha entre claro, escuro ou seguir o sistema",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { setThemePreference(ThemePreference.LIGHT) },
                                    enabled = themePreference != ThemePreference.LIGHT
                                ) {
                                    Text("Claro")
                                }
                                OutlinedButton(
                                    onClick = { setThemePreference(ThemePreference.DARK) },
                                    enabled = themePreference != ThemePreference.DARK
                                ) {
                                    Text("Escuro")
                                }
                                OutlinedButton(
                                    onClick = { setThemePreference(ThemePreference.SYSTEM) },
                                    enabled = themePreference != ThemePreference.SYSTEM
                                ) {
                                    Text("Sistema")
                                }
                            }
                        }
                    }
                }
            }

            LogoutButton(
                onClick = {
                    navController.navigate(Destination.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }
    }
}
