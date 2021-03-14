package com.android.androidtesttawkto.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.androidtesttawkto.room.profile.UserProfileCacheEntity
import com.android.androidtesttawkto.room.profile.UserProfileDao
import com.android.androidtesttawkto.room.user.UserCacheEntity
import com.android.androidtesttawkto.room.user.UserDao

@Database(
    entities = [UserCacheEntity::class,
        UserProfileCacheEntity::class], version = 1
)
abstract class UserDatabase : RoomDatabase() {

    companion object {
        val DATABASE_NAME = "UsersDB"
    }

    abstract fun userDao(): UserDao

    abstract fun userProfileDao(): UserProfileDao
}