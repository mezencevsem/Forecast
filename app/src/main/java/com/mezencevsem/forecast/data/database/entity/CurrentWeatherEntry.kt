package com.mezencevsem.forecast.data.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mezencevsem.forecast.data.database.converters.ListDataConverter

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val cloudcover: Int,
    val feelslike: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val precip: Double,
    val pressure: Int,
    val temperature: Double,
    @SerializedName("uv_index")
    val uvIndex: Int,
    val visibility: Double,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("weather_descriptions")
    @TypeConverters(ListDataConverter::class)
    val weatherDescriptions: List<String>,
    @SerializedName("weather_descriptions_ru")
    @TypeConverters(ListDataConverter::class)
    val weatherDescriptionsRu: List<String>?,
    @SerializedName("weather_icons")
    @TypeConverters(ListDataConverter::class)
    val weatherIcons: List<String>,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}