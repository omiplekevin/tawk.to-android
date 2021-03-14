package com.android.androidtesttawkto

import androidx.room.Room
import androidx.test.InstrumentationRegistry.getContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.androidtesttawkto.models.UserModel
import com.android.androidtesttawkto.models.UserProfileModel
import com.android.androidtesttawkto.room.UserDatabase
import com.android.androidtesttawkto.room.profile.UserProfileCacheEntity
import com.android.androidtesttawkto.room.profile.UserProfileCacheMapper
import com.android.androidtesttawkto.room.user.UserCacheEntity
import com.android.androidtesttawkto.room.user.UserCacheMapper
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CachedUserDaoTest {

    private lateinit var userDatabase: UserDatabase

    @Before
    fun initializeDb() {
        userDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            UserDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        userDatabase.close()
    }

    @Test
    fun testInsertUserData() {
        val userModel = UserModel(
            "https://avatars.githubusercontent.com/u/51?v=4",
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            "https://api.github.com/users/elbowdonkey/followers",
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            "https://github.com/elbowdonkey",
            999,
            "dummyData",
            "dummyData",
            "https://api.github.com/users/elbowdonkey/orgs",
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            false,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            "User",
            "https://api.github.com/users/elbowdonkey",
            true
        )
        val insertResponse = userDatabase.userDao().insertTest(UserCacheMapper().mapToEntity(userModel))
        Assert.assertEquals(userModel.id.toLong(), insertResponse)
    }

    @Test
    fun testUpdateUserData() {
        val baseUserModel = UserModel(
            "https://avatars.githubusercontent.com/u/51?v=4",
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            "https://api.github.com/users/elbowdonkey/followers",
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            "https://github.com/elbowdonkey",
            999,
            "dummyData",
            "dummyData",
            "https://api.github.com/users/elbowdonkey/orgs",
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            true,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            "User",
            "https://api.github.com/users/elbowdonkey",
            false
        )
        //insert the test data
        userDatabase.userDao().insertTest(UserCacheMapper().mapToEntity(baseUserModel))
        //fetch the latest inserted data
        val baseInsertedUser = userDatabase.userDao().getUserCachedTest(baseUserModel.id, baseUserModel.login)
        //update some fields
        baseInsertedUser.hasNotes = true
        baseInsertedUser.site_admin = true
        baseInsertedUser.gravatar_id = "http://www.google.com"
        baseInsertedUser.url = "https://api.github.com/users/elbowdonkey2"
        //apply the updates
        userDatabase.userDao().updateUserCacheTest(baseInsertedUser)
        //fetch the updated entry
        val updateUserData = userDatabase.userDao().getUserCachedTest(baseUserModel.id, baseUserModel.login)

        Assert.assertEquals(baseUserModel.id, updateUserData.id)
        Assert.assertEquals(true, updateUserData.hasNotes)
        Assert.assertEquals(true, updateUserData.site_admin)
        Assert.assertEquals("http://www.google.com", updateUserData.gravatar_id)
        Assert.assertEquals("https://api.github.com/users/elbowdonkey2", updateUserData.url)
    }

    @Test
    fun testInsertUserProfileData() {
        val userProfileModel = UserProfileModel(
            "https://avatars.githubusercontent.com/u/51?v=4",
            null,
            "http://collusioni.st",
            null,
            "2008-01-25T22:08:20Z",
            null,
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            25,
            "https://api.github.com/users/elbowdonkey/followers",
            9,
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            null,
            "https://github.com/elbowdonkey",
            888,
            "Portland, OR",
            "elbowdonkey",
            "Michael Buffington",
            "MDQ6VXNlcjUx",
            "https://api.github.com/users/elbowdonkey/orgs",
            4,
            45,
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            false,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            null,
            "User",
            "2021-03-10T23:02:30Z",
            "https://api.github.com/users/elbowdonkey",
            false,
            ""
            )
        val insertResponse = userDatabase.userProfileDao().insertTest(UserProfileCacheMapper().mapToEntity(userProfileModel))
        Assert.assertEquals(userProfileModel.id.toLong(), insertResponse)
    }

    @Test
    fun testUpdateUserProfileData() {
        val userProfileModel = UserProfileModel(
            "https://avatars.githubusercontent.com/u/51?v=4",
            null,
            "http://collusioni.st",
            null,
            "2008-01-25T22:08:20Z",
            null,
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            25,
            "https://api.github.com/users/elbowdonkey/followers",
            9,
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            null,
            "https://github.com/elbowdonkey",
            888,
            "Portland, OR",
            "elbowdonkey",
            "Michael Buffington",
            "MDQ6VXNlcjUx",
            "https://api.github.com/users/elbowdonkey/orgs",
            4,
            45,
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            false,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            null,
            "User",
            "2021-03-10T23:02:30Z",
            "https://api.github.com/users/elbowdonkey",
            false,
            ""
        )
        //insert original data
        userDatabase.userProfileDao().insertTest(UserProfileCacheMapper().mapToEntity(userProfileModel))
        //fetch inserted data
        val userProfileEntity = userDatabase.userProfileDao().getUserProfileCacheTest(userProfileModel.login!!, userProfileModel.id)
        Assert.assertNotNull(userProfileEntity)
        userProfileEntity?.hasNotes = true
        userProfileEntity?.notes = "this is a test content!@#$%^&*()_+"
        //update the database with new data
        userDatabase.userProfileDao().updateProfileTest(userProfileEntity!!)
        //fetch the updated data
        val updatedUserProfile = userDatabase.userProfileDao().getUserProfileCacheTest(userProfileModel.login!!, userProfileModel.id)
        Assert.assertNotNull(updatedUserProfile)
        Assert.assertEquals(true, updatedUserProfile?.hasNotes)
        Assert.assertEquals("this is a test content!@#\$%^&*()_+", updatedUserProfile?.notes)
    }

    @Test
    fun testUserDataDomainToCacheEntityMapper() {
        val userModel = UserModel(
            "https://avatars.githubusercontent.com/u/51?v=4",
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            "https://api.github.com/users/elbowdonkey/followers",
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            "https://github.com/elbowdonkey",
            999,
            "dummyData",
            "dummyData",
            "https://api.github.com/users/elbowdonkey/orgs",
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            false,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            "User",
            "https://api.github.com/users/elbowdonkey",
            true
        )
        val userCacheEntity: UserCacheEntity = UserCacheMapper().mapToEntity(userModel)

        Assert.assertEquals(userModel.avatar_url, userCacheEntity.avatar_url)
        Assert.assertEquals(userModel.events_url, userCacheEntity.events_url)
        Assert.assertEquals(userModel.followers_url, userCacheEntity.followers_url)
        Assert.assertEquals(userModel.following_url, userCacheEntity.following_url)
        Assert.assertEquals(userModel.gists_url, userCacheEntity.gists_url)
        Assert.assertEquals(userModel.gravatar_id, userCacheEntity.gravatar_id)
        Assert.assertEquals(userModel.html_url, userCacheEntity.html_url)
        Assert.assertEquals(userModel.id, userCacheEntity.id)
        Assert.assertEquals(userModel.login, userCacheEntity.login)
        Assert.assertEquals(userModel.node_id, userCacheEntity.node_id)
        Assert.assertEquals(userModel.organizations_url, userCacheEntity.organizations_url)
        Assert.assertEquals(userModel.received_events_url, userCacheEntity.received_events_url)
        Assert.assertEquals(userModel.repos_url, userCacheEntity.repos_url)
        Assert.assertEquals(userModel.site_admin, userCacheEntity.site_admin)
        Assert.assertEquals(userModel.starred_url, userCacheEntity.starred_url)
        Assert.assertEquals(userModel.subscriptions_url, userCacheEntity.subscriptions_url)
        Assert.assertEquals(userModel.type, userCacheEntity.type)
        Assert.assertEquals(userModel.url, userCacheEntity.url)
        Assert.assertEquals(userModel.hasNotes, userCacheEntity.hasNotes)
    }

    @Test
    fun testUserDataCacheEntityToDomainMapper() {
        val userCacheEntity = UserCacheEntity(
            999,
            "https://avatars.githubusercontent.com/u/51?v=4",
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            "https://api.github.com/users/elbowdonkey/followers",
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            "https://github.com/elbowdonkey",
            "dummyData",
            "dummyData",
            "https://api.github.com/users/elbowdonkey/orgs",
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            false,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            "User",
            "https://api.github.com/users/elbowdonkey",
            true
        )

        val userModel: UserModel = UserCacheMapper().mapFromEntity(userCacheEntity)

        Assert.assertEquals(userCacheEntity.avatar_url, userModel.avatar_url)
        Assert.assertEquals(userCacheEntity.events_url, userModel.events_url)
        Assert.assertEquals(userCacheEntity.followers_url, userModel.followers_url)
        Assert.assertEquals(userCacheEntity.following_url, userModel.following_url)
        Assert.assertEquals(userCacheEntity.gists_url, userModel.gists_url)
        Assert.assertEquals(userCacheEntity.gravatar_id, userModel.gravatar_id)
        Assert.assertEquals(userCacheEntity.html_url, userModel.html_url)
        Assert.assertEquals(userCacheEntity.id, userModel.id)
        Assert.assertEquals(userCacheEntity.login, userModel.login)
        Assert.assertEquals(userCacheEntity.node_id, userModel.node_id)
        Assert.assertEquals(userCacheEntity.organizations_url, userModel.organizations_url)
        Assert.assertEquals(userCacheEntity.received_events_url, userModel.received_events_url)
        Assert.assertEquals(userCacheEntity.repos_url, userModel.repos_url)
        Assert.assertEquals(userCacheEntity.site_admin, userModel.site_admin)
        Assert.assertEquals(userCacheEntity.starred_url, userModel.starred_url)
        Assert.assertEquals(userCacheEntity.subscriptions_url, userModel.subscriptions_url)
        Assert.assertEquals(userCacheEntity.type, userModel.type)
        Assert.assertEquals(userCacheEntity.url, userModel.url)
        Assert.assertEquals(userCacheEntity.hasNotes, userModel.hasNotes)
    }

    @Test
    fun testUserProfileDomainToCacheEntityMapper() {
        val userProfileModel = UserProfileModel(
            "https://avatars.githubusercontent.com/u/51?v=4",
            null,
            "http://collusioni.st",
            null,
            "2008-01-25T22:08:20Z",
            null,
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            25,
            "https://api.github.com/users/elbowdonkey/followers",
            9,
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            null,
            "https://github.com/elbowdonkey",
            888,
            "Portland, OR",
            "elbowdonkey",
            "Michael Buffington",
            "MDQ6VXNlcjUx",
            "https://api.github.com/users/elbowdonkey/orgs",
            4,
            45,
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            false,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            null,
            "User",
            "2021-03-10T23:02:30Z",
            "https://api.github.com/users/elbowdonkey",
            false,
            "this is a sample notes from the user 1234567890!@#$%^&*()_+{}:<>?[];',./'"
        )
        val userProfileCacheEntity: UserProfileCacheEntity = UserProfileCacheMapper()
            .mapToEntity(userProfileModel)

        Assert.assertEquals(userProfileModel.avatar_url, userProfileCacheEntity.avatar_url)
        Assert.assertEquals(userProfileModel.events_url, userProfileCacheEntity.events_url)
        Assert.assertEquals(userProfileModel.followers_url, userProfileCacheEntity.followers_url)
        Assert.assertEquals(userProfileModel.following_url, userProfileCacheEntity.following_url)
        Assert.assertEquals(userProfileModel.gists_url, userProfileCacheEntity.gists_url)
        Assert.assertEquals(userProfileModel.gravatar_id, userProfileCacheEntity.gravatar_id)
        Assert.assertEquals(userProfileModel.html_url, userProfileCacheEntity.html_url)
        Assert.assertEquals(userProfileModel.id, userProfileCacheEntity.id)
        Assert.assertEquals(userProfileModel.login, userProfileCacheEntity.login)
        Assert.assertEquals(userProfileModel.node_id, userProfileCacheEntity.node_id)
        Assert.assertEquals(userProfileModel.organizations_url, userProfileCacheEntity.organizations_url)
        Assert.assertEquals(userProfileModel.received_events_url, userProfileCacheEntity.received_events_url)
        Assert.assertEquals(userProfileModel.repos_url, userProfileCacheEntity.repos_url)
        Assert.assertEquals(userProfileModel.site_admin, userProfileCacheEntity.site_admin)
        Assert.assertEquals(userProfileModel.starred_url, userProfileCacheEntity.starred_url)
        Assert.assertEquals(userProfileModel.subscriptions_url, userProfileCacheEntity.subscriptions_url)
        Assert.assertEquals(userProfileModel.type, userProfileCacheEntity.type)
        Assert.assertEquals(userProfileModel.url, userProfileCacheEntity.url)
        Assert.assertEquals(userProfileModel.hasNotes, userProfileCacheEntity.hasNotes)
    }

    @Test
    fun testUserProfileCacheEntityToDomainMapper() {
        val userProfileCacheEntity = UserProfileCacheEntity(
            888,
            "https://avatars.githubusercontent.com/u/51?v=4",
            null,
            "http://collusioni.st",
            null,
            "2008-01-25T22:08:20Z",
            null,
            "https://api.github.com/users/elbowdonkey/events{/privacy}",
            25,
            "https://api.github.com/users/elbowdonkey/followers",
            9,
            "https://api.github.com/users/elbowdonkey/following{/other_user}",
            "https://api.github.com/users/elbowdonkey/gists{/gist_id}",
            "",
            null,
            "https://github.com/elbowdonkey",
            "Portland, OR",
            "elbowdonkey",
            "Michael Buffington",
            "MDQ6VXNlcjUx",
            "https://api.github.com/users/elbowdonkey/orgs",
            4,
            45,
            "https://api.github.com/users/elbowdonkey/received_events",
            "https://api.github.com/users/elbowdonkey/repos",
            false,
            "https://api.github.com/users/elbowdonkey/starred{/owner}{/repo}",
            "https://api.github.com/users/elbowdonkey/subscriptions",
            null,
            "User",
            "2021-03-10T23:02:30Z",
            "https://api.github.com/users/elbowdonkey",
            false,
            "this is a sample notes from the user 1234567890!@#$%^&*()_+{}:<>?[];',./'"
        )
        val userModel: UserProfileModel = UserProfileCacheMapper()
            .mapFromEntity(userProfileCacheEntity)

        Assert.assertEquals(userProfileCacheEntity.avatar_url, userModel.avatar_url)
        Assert.assertEquals(userProfileCacheEntity.events_url, userModel.events_url)
        Assert.assertEquals(userProfileCacheEntity.followers_url, userModel.followers_url)
        Assert.assertEquals(userProfileCacheEntity.following_url, userModel.following_url)
        Assert.assertEquals(userProfileCacheEntity.gists_url, userModel.gists_url)
        Assert.assertEquals(userProfileCacheEntity.gravatar_id, userModel.gravatar_id)
        Assert.assertEquals(userProfileCacheEntity.html_url, userModel.html_url)
        Assert.assertEquals(userProfileCacheEntity.id, userModel.id)
        Assert.assertEquals(userProfileCacheEntity.login, userModel.login)
        Assert.assertEquals(userProfileCacheEntity.node_id, userModel.node_id)
        Assert.assertEquals(userProfileCacheEntity.organizations_url, userModel.organizations_url)
        Assert.assertEquals(userProfileCacheEntity.received_events_url, userModel.received_events_url)
        Assert.assertEquals(userProfileCacheEntity.repos_url, userModel.repos_url)
        Assert.assertEquals(userProfileCacheEntity.site_admin, userModel.site_admin)
        Assert.assertEquals(userProfileCacheEntity.starred_url, userModel.starred_url)
        Assert.assertEquals(userProfileCacheEntity.subscriptions_url, userModel.subscriptions_url)
        Assert.assertEquals(userProfileCacheEntity.type, userModel.type)
        Assert.assertEquals(userProfileCacheEntity.url, userModel.url)
        Assert.assertEquals(userProfileCacheEntity.hasNotes, userModel.hasNotes)
    }
}