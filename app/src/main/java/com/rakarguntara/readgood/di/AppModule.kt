package com.rakarguntara.readgood.di

import com.rakarguntara.readgood.BuildConfig
import com.rakarguntara.readgood.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApi(): ApiService{
        val loggingInterceptor = if(BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).client(client).build()

        return retrofit.create(ApiService::class.java)
    }
}