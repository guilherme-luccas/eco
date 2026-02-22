package br.com.fiap.eco.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Co2
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.fiap.eco.ui.theme.EcoAccentBlue
import br.com.fiap.eco.ui.theme.EcoAccentOrange

@Composable
fun ImpactSummaryCard(
    co2Avoided: String = "210 kg",
    waterSaved: String = "1.500 L",
    plasticAvoided: String = "85 itens"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Resumo do Impacto",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            ImpactRow(
                icon = Icons.Default.Co2,
                label = "CO₂ Evitado",
                value = co2Avoided,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            ImpactRow(
                icon = Icons.Default.WaterDrop,
                label = "Água Poupada",
                value = waterSaved,
                color = EcoAccentBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            ImpactRow(
                icon = Icons.Default.Delete,
                label = "Plástico Evitado",
                value = plasticAvoided,
                color = EcoAccentOrange
            )
        }
    }
}
