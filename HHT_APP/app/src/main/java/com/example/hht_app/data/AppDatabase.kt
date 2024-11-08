package com.example.hht_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hht_app.data.dao.BSMDao
import com.example.hht_app.data.model.BSM

@Database(
    entities = [BSM::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bsmDao(): BSMDao
    companion object {
        const val DATABASE_NAME = "app_database"
    }
}