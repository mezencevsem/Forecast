package com.mezencevsem.forecast.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.data.database.converters.ListDataConverter

@Database(
    entities = [CurrentWeatherEntry::class],
    version = 1
    )
@TypeConverters(ListDataConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDAO() : CurrentWeatherDAO

    companion object {
        @Volatile
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    ForecastDatabase::class.java,
                    "forecast.db")
                .build()
    }
}