package com.android.androidtesttawkto.di

import com.android.androidtesttawkto.network.user.UserNetworkMapper
import com.android.androidtesttawkto.network.GitHubRetrofitInterface
import com.android.androidtesttawkto.network.profile.UserProfileNetworkMapper
import com.android.androidtesttawkto.repository.MainRepository
import com.android.androidtesttawkto.room.profile.UserProfileCacheMapper
import com.android.androidtesttawkto.room.profile.UserProfileDao
import com.android.androidtesttawkto.room.user.UserCacheMapper
import com.android.androidtesttawkto.room.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providesMainRepository(
        userDao: UserDao,
        userProfileDao: UserProfileDao,
        gitHubRetrofitInterface: GitHubRetrofitInterface,
        userCacheMapper: UserCacheMapper,
        userNetworkMapper: UserNetworkMapper,
        userProfileCacheMapper: UserProfileCacheMapper,
        userProfileNetworkMapper: UserProfileNetworkMapper
    ): MainRepository {
        return MainRepository(
            userDao,
            userProfileDao,
            gitHubRetrofitInterface,
            userCacheMapper,
            userNetworkMapper,
            userProfileCacheMapper,
            userProfileNetworkMapper
        )
    }
}