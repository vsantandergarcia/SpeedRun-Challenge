package com.vsantander.speedrun.di

import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.repository.utils.CacheTimer
import com.vsantander.speedrun.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSpeedRunWebService(): SpeedRunWebService {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.readTimeout(Constants.TIMEOUT_WS.toLong(), TimeUnit.SECONDS)
        clientBuilder.connectTimeout(Constants.TIMEOUT_WS.toLong(), TimeUnit.SECONDS)

        return Retrofit.Builder()
                .baseUrl(Constants.APP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build()
                .create<SpeedRunWebService>(SpeedRunWebService::class.java)
    }

    @Provides
    fun provideCacheTimer(): CacheTimer {
        return CacheTimer(true,
                Constants.TIMEOUT_CACHE_REPOSITORY.toLong(), TimeUnit.MINUTES)
    }
}