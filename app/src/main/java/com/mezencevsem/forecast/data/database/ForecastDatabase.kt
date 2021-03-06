package com.mezencevsem.forecast.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mezencevsem.forecast.data.database.converters.ListDataConverter
import com.mezencevsem.forecast.data.database.converters.LocalDateConverter
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry
import com.mezencevsem.forecast.data.database.entity.Request
import com.mezencevsem.forecast.data.database.entity.WeatherLocation

@Database(
    entities = [
        CurrentWeatherEntry::class,
        FutureWeatherEntry::class,
        WeatherLocation::class,
        Request::class
    ],
    version = 1
)
@TypeConverters(
    ListDataConverter::class,
    LocalDateConverter::class
)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDAO(): CurrentWeatherDAO
    abstract fun futureWeatherDAO(): FutureWeatherDAO
    abstract fun weatherLocationDAO(): WeatherLocationDAO
    abstract fun requestDAO(): RequestDAO

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
                "forecast.db"
            ).build()
    }
}