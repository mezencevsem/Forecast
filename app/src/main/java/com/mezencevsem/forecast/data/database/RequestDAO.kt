package com.mezencevsem.forecast.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mezencevsem.forecast.data.database.entity.Request
import com.mezencevsem.forecast.data.database.entity.WEATHER_REQUEST_INFO_ID

@Dao
interface RequestDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(request: Request)

    @Query("select * from weather_request_info where id = $WEATHER_REQUEST_INFO_ID")
    fun getRequestInfo(): LiveData<Request>
}