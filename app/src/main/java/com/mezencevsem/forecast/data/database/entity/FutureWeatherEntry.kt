package com.mezencevsem.forecast.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mezencevsem.forecast.data.database.converters.LocalDateConverter
import com.mezencevsem.forecast.data.network.response.future.unused.Astro
import org.threeten.bp.LocalDate

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    //val astro: Astro,
    val avgtemp: Int,
    @TypeConverters(LocalDateConverter::class)
    val date: LocalDate,
    /*@SerializedName("date_epoch")
    val dateEpoch: Int,*/
    val maxtemp: Int,
    val mintemp: Int,
    val sunhour: Double,
    val totalsnow: Int,
    @SerializedName("uv_index")
    val uvIndex: Int
)