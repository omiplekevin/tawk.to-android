package com.android.androidtesttawkto.di

import com.android.androidtesttawkto.BuildConfig
import com.android.androidtesttawkto.network.GitHubRetrofitInterface
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
object RetrofitModule {

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    }

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_USERS_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun providesUsersService(retrofit: Retrofit.Builder): GitHubRetrofitInterface {
        return retrofit
            .build()
            .create(GitHubRetrofitInterface::class.java)
    }
}