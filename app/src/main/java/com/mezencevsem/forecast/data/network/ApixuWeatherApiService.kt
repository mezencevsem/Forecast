package com.mezencevsem.forecast.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mezencevsem.forecast.data.network.response.current.CurrentWeatherResponse
import com.mezencevsem.forecast.data.network.response.future.FutureWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://api.weatherstack.com/"
const val API_KEY = "a07f82e48da47c39f68050ea167ef90b"

interface ApixuWeatherApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("units") unitSystem: String,
        @Query("language") language: String
    ): Deferred<CurrentWeatherResponse>

    @GET("forecast")
    fun getFutureWeather(
        @Query("query") location: String,
        @Query("forecast_days") days: Int,
        @Query("units") unitSystem: String,
        @Query("language") language: String
    ): Deferred<FutureWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApixuWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request().url()
                    .newBuilder()
                    .addQueryParameter(
                        "access_key",
                        API_KEY
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)
        }
    }
}