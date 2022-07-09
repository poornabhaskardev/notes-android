package com.poornabhaskar.notes.di

import com.poornabhaskar.notes.api.UserApi
import com.poornabhaskar.notes.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    }

    @Singleton
    @Provides
    fun providesUserApi(retrofit: Retrofit) : UserApi{
        return retrofit.create(UserApi::class.java)
    }
}