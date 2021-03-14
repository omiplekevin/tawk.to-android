package com.android.androidtesttawkto.network

import com.android.androidtesttawkto.network.profile.UserProfileNetworkEntity
import com.android.androidtesttawkto.network.user.UserNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubRetrofitInterface {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int
    ): List<UserNetworkEntity>

    @GET("users/{username}")
    suspend fun getUserProfile(@Path("username") username: String):
            UserProfileNetworkEntity

}