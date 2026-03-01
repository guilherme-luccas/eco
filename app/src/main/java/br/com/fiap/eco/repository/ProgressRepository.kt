package br.com.fiap.eco.repository

data class TodayProgress(
    val completedHabitIndexes: List<Int>,
    val pointsOfDay: Int
)

data class UserStats(
    val totalPoints: Int,
    val bestStreak: Int
)

data class UserImpact(
    val co2Avoided: String,
    val waterSaved: String,
    val plasticAvoided: String
)

interface ProgressRepository {
    fun getTodayProgress(userId: Int): TodayProgress?
    fun saveDayProgress(userId: Int, date: String, completedHabitIndexes: List<Int>, pointsOfDay: Int)
    fun getUserStats(userId: Int): UserStats
    fun getTodayImpact(userId: Int): UserImpact
    fun getTotalImpact(userId: Int): UserImpact
}
