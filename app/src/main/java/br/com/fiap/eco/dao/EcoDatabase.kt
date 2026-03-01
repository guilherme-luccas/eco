package br.com.fiap.eco.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.eco.model.DayProgress
import br.com.fiap.eco.model.User

@Database(entities = [User::class, DayProgress::class], version = 2)
abstract class EcoDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dayProgressDao(): DayProgressDao

    companion object {
        private lateinit var instance: EcoDatabase
        fun getDatabase(context: Context): EcoDatabase {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        EcoDatabase::class.java,
                        "eco_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(true)
                    .build()
            }
            return instance
        }
    }
}
