package com.android.androidtesttawkto.room.profile

import androidx.room.*

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userProfileCacheEntity: UserProfileCacheEntity): Long

    @Update
    suspend fun updateProfile(updatedUserProfile: UserProfileCacheEntity)

    @Query("SELECT * FROM profiles WHERE id = :id AND login = :login")
    suspend fun getUserProfileCache(login: String, id: Int): UserProfileCacheEntity?

    ///////////////////////////////////////////////////////////////////////////
    // TEST FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTest(userProfileCacheEntity: UserProfileCacheEntity): Long

    @Update
    fun updateProfileTest(updatedUserProfile: UserProfileCacheEntity)

    @Query("SELECT * FROM profiles WHERE id = :id AND login = :login")
    fun getUserProfileCacheTest(login: String, id: Int): UserProfileCacheEntity?

}