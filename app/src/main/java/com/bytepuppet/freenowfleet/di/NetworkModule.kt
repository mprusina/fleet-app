package com.bytepuppet.freenowfleet.di

import com.bytepuppet.freenowfleet.data.api.VehicleService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://fake-poi-api.mytaxi.com/"

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): VehicleService {
        return retrofit.create(VehicleService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().create()
}