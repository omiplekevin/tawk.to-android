package com.android.androidtesttawkto.room.user

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userCacheEntity: UserCacheEntity): Long

    @Query("SELECT * FROM users")
    suspend fun getUsersCache(): List<UserCacheEntity>

    @Query("SELECT * FROM users WHERE id = :id AND login = :login")
    suspend fun getUserCached(id: Int, login: String): UserCacheEntity

    @Query("SELECT * FROM users LIMIT :limit OFFSET :offset")
    suspend fun getRangedUsersCache(limit: Int, offset: Int): List<UserCacheEntity>

    @Query("SELECT * FROM users WHERE login LIKE :keyword")
    suspend fun searchUserDetail(keyword: String): List<UserCacheEntity>

    @Update
    suspend fun updateUserCache(updatedUserCacheEntity: UserCacheEntity)

    ///////////////////////////////////////////////////////////////////////////
    // TEST FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTest(userCacheEntity: UserCacheEntity): Long

    @Query("SELECT * FROM users WHERE id = :id AND login = :login")
    fun getUserCachedTest(id: Int, login: String): UserCacheEntity

    @Update
    fun updateUserCacheTest(updatedUserCacheEntity: UserCacheEntity)
}