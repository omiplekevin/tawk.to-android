package com.android.androidtesttawkto.repository

import com.android.androidtesttawkto.models.UserModel
import com.android.androidtesttawkto.models.UserProfileModel
import com.android.androidtesttawkto.network.user.UserNetworkMapper
import com.android.androidtesttawkto.network.GitHubRetrofitInterface
import com.android.androidtesttawkto.network.profile.UserProfileNetworkMapper
import com.android.androidtesttawkto.room.profile.UserProfileCacheMapper
import com.android.androidtesttawkto.room.profile.UserProfileDao
import com.android.androidtesttawkto.room.user.UserCacheMapper
import com.android.androidtesttawkto.room.user.UserDao
import com.android.androidtesttawkto.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MainRepository constructor(
    private val userDao: UserDao,
    private val userProfileDao: UserProfileDao,
    private val gitHubRetrofitInterface: GitHubRetrofitInterface,
    private val userCacheMapper: UserCacheMapper,
    private val userNetworkMapper: UserNetworkMapper,
    private val userProfileCacheMapper: UserProfileCacheMapper,
    private val userProfileNetworkMapper: UserProfileNetworkMapper
) {
    //declare variable for page size, initialized as 0
    private var pageSize: Int = 0

    /**
     * startId: Int - this is the 'since' parameter on the GitHub URL, this is also used for paging
     * loadMore: Boolean - this flags if the request is intented to load more from previous range
     */
    suspend fun getUsers(startId: Int, offset: Int, loadMore: Boolean): Flow<DataState<List<UserModel>>> = flow {
        emit(DataState.Loading)
        try {

            if (!loadMore) {
                //fetch first cached if there is
                val initialCached = userDao.getUsersCache()
                //emit the cached data
                emit(DataState.Success(userCacheMapper.mapFromEntityList(initialCached)))
            }

            val liveUsers = gitHubRetrofitInterface.getUsers(startId)
            val users = userNetworkMapper.mapFromEntityList(liveUsers)
            //if page size is 0, then count the live data response and set as pageSize count
            //this will only happen once
            if (pageSize == 0) {
                //set page size
                pageSize = users.size
                Timber.d("initializing paging size = $pageSize")
            }
            Timber.d("paging size = $pageSize")
            for (user in users) {
                userDao.insert(userCacheMapper.mapToEntity(user))
            }
            Timber.d("pagesize: $pageSize, offset: ${offset-1}")
            //offset is minus 1 because of the indexing from the DB
            //Query as follows: SELECT * FROM users LIMIT :limit OFFSET :offset
            //offset came from the current loaded items from the recyclerview
            val cachedUsers = userDao.getRangedUsersCache(pageSize, offset-1)

            if (!loadMore) {
                //if first time load of users list
                emit(DataState.Success(userCacheMapper.mapFromEntityList(cachedUsers)))
            } else {
                //if list is currently paging
                emit(DataState.LoadMore(userCacheMapper.mapFromEntityList(cachedUsers)))
            }
        } catch (e: Exception) {
            val cachedUsers = userDao.getUsersCache()
            if (cachedUsers.isNotEmpty()) {
                emit(DataState.Success(userCacheMapper.mapFromEntityList(cachedUsers)))
            } else {
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun getUserProfile(username: String, id: Int): Flow<DataState<UserProfileModel>> = flow {
        emit(DataState.Loading)
        try {
            var cachedUserProfile = userProfileDao.getUserProfileCache(username, id)
            if (cachedUserProfile != null) {
                emit(DataState.Success(userProfileCacheMapper.mapFromEntity(cachedUserProfile)))
            } else {
                emit(DataState.Error(java.lang.Exception("profile is null!")))
            }

            val liveUserProfile = gitHubRetrofitInterface.getUserProfile(username)
            val userProfile = userProfileNetworkMapper.mapFromEntity(liveUserProfile)
            userProfileDao.insert(userProfileCacheMapper.mapToEntity(userProfile))
            //overwrite current fetch
            cachedUserProfile = userProfileDao.getUserProfileCache(username, id)

            if (cachedUserProfile != null) {
                emit(DataState.Success(userProfileCacheMapper.mapFromEntity(cachedUserProfile)))
            } else {
                emit(DataState.Error(java.lang.Exception("profile is null!")))
            }
        } catch (e: Exception) {
            val cachedProfile = userProfileDao.getUserProfileCache(username, id)
            if (cachedProfile != null) {
                emit(DataState.Success(userProfileCacheMapper.mapFromEntity(cachedProfile)))
            } else {
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun searchUserDetail(keyword: String): Flow<DataState<List<UserModel>>> = flow {
        emit(DataState.Loading)
        try {
            val cachedUsers = userDao.searchUserDetail("%".plus(keyword).plus("%"))
            for (user in cachedUsers) {
                Timber.d("emitting: ${user.id}")
            }
            emit(DataState.Success(userCacheMapper.mapFromEntityList(cachedUsers)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun saveUserNotes(updatedUserProfile: UserProfileModel): Flow<DataState<UserProfileModel>> = flow {
        emit(DataState.Loading)
        try {
            //update user profile
            userProfileDao.updateProfile(UserProfileCacheMapper().mapToEntity(updatedUserProfile))
            //fetch user the updated user profile
            val userProfile = userProfileDao.getUserProfileCache(updatedUserProfile.login!!, updatedUserProfile.id)

            //fetch from users cache
            var cachedUser = userDao.getUserCached(updatedUserProfile.id, updatedUserProfile.login)
            //set to hasNotes true
            cachedUser.hasNotes = updatedUserProfile.hasNotes
            //update the cached user data
            userDao.updateUserCache(cachedUser)

            if (userProfile != null) {
                emit(DataState.Success(userProfileCacheMapper.mapFromEntity(userProfile)))
            } else {
                emit(DataState.Error(java.lang.Exception("profile is null!")))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}