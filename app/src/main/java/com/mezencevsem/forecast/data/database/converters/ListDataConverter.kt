package com.mezencevsem.forecast.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object ListDataConverter {
    @JvmStatic
    @TypeConverter
    fun fromJson(value: String?): List<String>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}