package br.com.fiap.eco.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.eco.model.DayProgress

@Dao
interface DayProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(progress: DayProgress): Long

    @Update
    fun update(progress: DayProgress): Int

    @Query("SELECT * FROM tb_day_progress WHERE userId = :userId AND date = :date LIMIT 1")
    fun getByUserAndDate(userId: Int, date: String): DayProgress?

    @Query("SELECT * FROM tb_day_progress WHERE userId = :userId ORDER BY date ASC")
    fun getAllByUser(userId: Int): List<DayProgress>
}
