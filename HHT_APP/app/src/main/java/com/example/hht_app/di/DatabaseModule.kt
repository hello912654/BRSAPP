package com.example.hht_app.di

import android.content.Context
import androidx.room.Room
import com.example.hht_app.data.AppDatabase
import com.example.hht_app.data.dao.BSMDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "baggage_database"
        )
        .fallbackToDestructiveMigration()  //allow to drop and recreate the database
        .build()
    }

    @Provides
    fun provideBSMDao(database: AppDatabase): BSMDao {
        return database.bsmDao()
    }
}