package br.com.fiap.eco.repository

import android.content.Context
import br.com.fiap.eco.dao.EcoDatabase
import br.com.fiap.eco.model.DayProgress
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RoomProgressRepository(context: Context) : ProgressRepository {
    private val db = EcoDatabase.getDatabase(context)
    private val userDao = db.userDao()
    private val dayProgressDao = db.dayProgressDao()

    private fun todayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Calendar.getInstance().time)
    }

    private fun parseIndexes(s: String): List<Int> {
        if (s.isBlank()) return emptyList()
        return s.split(",").mapNotNull { it.trim().toIntOrNull() }
    }

    private fun indexesToString(indexes: List<Int>): String {
        return indexes.sorted().joinToString(",")
    }

    override fun getTodayProgress(userId: Int): TodayProgress? {
        val date = todayDate()
        val progress = dayProgressDao.getByUserAndDate(userId, date) ?: return null
        return TodayProgress(
            completedHabitIndexes = parseIndexes(progress.completedHabitIndexes),
            pointsOfDay = progress.pointsOfDay
        )
    }

    override fun saveDayProgress(
        userId: Int,
        date: String,
        completedHabitIndexes: List<Int>,
        pointsOfDay: Int
    ) {
        val indexesStr = indexesToString(completedHabitIndexes)
        val existing = dayProgressDao.getByUserAndDate(userId, date)
        val progress = if (existing != null) {
            existing.copy(completedHabitIndexes = indexesStr, pointsOfDay = pointsOfDay)
        } else {
            DayProgress(userId = userId, date = date, completedHabitIndexes = indexesStr, pointsOfDay = pointsOfDay)
        }
        if (existing != null) {
            dayProgressDao.update(progress)
        } else {
            dayProgressDao.insert(progress)
        }
        refreshUserStats(userId)
    }

    private fun refreshUserStats(userId: Int) {
        val all = dayProgressDao.getAllByUser(userId)
        val totalPoints = all.sumOf { it.pointsOfDay }
        val bestStreak = computeBestStreak(all.map { it.date }.distinct().sorted())
        val lastActivityDate = all.maxByOrNull { it.date }?.date
        val user = userDao.getUserById(userId)
        userDao.update(
            user.copy(
                totalPoints = totalPoints,
                bestStreak = bestStreak,
                lastActivityDate = lastActivityDate
            )
        )
    }

    private fun computeBestStreak(sortedDates: List<String>): Int {
        if (sortedDates.isEmpty()) return 0
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var best = 1
        var current = 1
        for (i in 1 until sortedDates.size) {
            val prev = dateFormat.parse(sortedDates[i - 1]) ?: continue
            val curr = dateFormat.parse(sortedDates[i]) ?: continue
            val calPrev = Calendar.getInstance().apply { time = prev }
            val calCurr = Calendar.getInstance().apply { time = curr }
            calPrev.add(Calendar.DAY_OF_MONTH, 1)
            if (calPrev.get(Calendar.YEAR) == calCurr.get(Calendar.YEAR) &&
                calPrev.get(Calendar.DAY_OF_YEAR) == calCurr.get(Calendar.DAY_OF_YEAR)
            ) {
                current++
                best = maxOf(best, current)
            } else {
                current = 1
            }
        }
        return best
    }

    override fun getTodayImpact(userId: Int): UserImpact {
        val progress = getTodayProgress(userId)
        return calculateImpact(progress?.completedHabitIndexes ?: emptyList())
    }

    override fun getTotalImpact(userId: Int): UserImpact {
        val all = dayProgressDao.getAllByUser(userId)
        val allIndexes = all.flatMap { parseIndexes(it.completedHabitIndexes) }
        return calculateImpact(allIndexes)
    }

    private fun calculateImpact(indexes: List<Int>): UserImpact {
        var co2 = 0.0
        var water = 0
        var plastic = 0

        indexes.forEach { index ->
            when (index) {
                0 -> plastic += 1 // Reciclei plástico
                1 -> plastic += 1 // Usei sacola reutilizável
                2 -> water += 20 // Economizei água
                3 -> co2 += 1.5 // Fui a pé ou de bike
                4 -> plastic += 1 // Evitei descartáveis
            }
        }

        return UserImpact(
            co2Avoided = if (co2 >= 1.0) "%.1f kg".format(Locale.US, co2) else "%.0f g".format(Locale.US, co2 * 1000),
            waterSaved = if (water >= 1000) "%.1f L".format(Locale.US, water / 1000.0) else "$water L",
            plasticAvoided = "$plastic itens"
        )
    }

    override fun getUserStats(userId: Int): UserStats {
        val user = userDao.getUserById(userId)
        return UserStats(totalPoints = user.totalPoints, bestStreak = user.bestStreak)
    }
}
