package br.com.fiap.eco.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tb_day_progress",
    indices = [Index(value = ["userId", "date"], unique = true)]
)
data class DayProgress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val date: String,
    val completedHabitIndexes: String,
    val pointsOfDay: Int
)
