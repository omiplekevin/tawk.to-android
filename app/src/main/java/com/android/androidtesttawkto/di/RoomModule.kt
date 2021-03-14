package com.android.androidtesttawkto.di

import android.content.Context
import androidx.room.Room
import com.android.androidtesttawkto.room.user.UserDao
import com.android.androidtesttawkto.room.UserDatabase
import com.android.androidtesttawkto.room.profile.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun providesUserDB(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesUserDAO(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

    @Singleton
    @Provides
    fun providesUserProfileDAO(userDatabase: UserDatabase): UserProfileDao {
        return userDatabase.userProfileDao()
    }
}