package com.android.androidtesttawkto.room.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profiles")
data class UserProfileCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String?,

    @ColumnInfo(name = "bio")
    var bio: String?,

    @ColumnInfo(name = "blog")
    var blog: String?,

    @ColumnInfo(name = "company")
    var company: String?,

    @ColumnInfo(name = "created_at")
    var created_at: String?,

    @ColumnInfo(name = "email")
    var email: String?,

    @ColumnInfo(name = "events_url")
    var events_url: String?,

    @ColumnInfo(name = "followers")
    var followers: Int,

    @ColumnInfo(name = "followers_url")
    var followers_url: String?,

    @ColumnInfo(name = "following")
    var following: Int,

    @ColumnInfo(name = "following_url")
    var following_url: String?,

    @ColumnInfo(name = "gists_url")
    var gists_url: String?,

    @ColumnInfo(name = "gravatar_id")
    var gravatar_id: String?,

    @ColumnInfo(name = "hireable")
    var hireable: String?,

    @ColumnInfo(name = "html_url")
    var html_url: String?,

    @ColumnInfo(name = "location")
    var location: String?,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "node_id")
    var node_id: String?,

    @ColumnInfo(name = "organizations_url")
    var organizations_url: String?,

    @ColumnInfo(name = "public_gists")
    var public_gists: Int,

    @ColumnInfo(name = "public_repos")
    var public_repos: Int,

    @ColumnInfo(name = "received_events_url")
    var received_events_url: String?,

    @ColumnInfo(name = "repos_url")
    var repos_url: String?,

    @ColumnInfo(name = "site_admin")
    var site_admin: Boolean,

    @ColumnInfo(name = "starred_url")
    var starred_url: String?,

    @ColumnInfo(name = "subscriptions_url")
    var subscriptions_url: String?,

    @ColumnInfo(name = "twitter_username")
    var twitter_username: String?,

    @ColumnInfo(name = "type")
    var type: String?,

    @ColumnInfo(name = "updated_at")
    var updated_at: String?,

    @ColumnInfo(name = "url")
    var url: String?,

    @ColumnInfo(name = "hasNotes", defaultValue = "false")
    var hasNotes: Boolean,

    @ColumnInfo(name = "notes")
    var notes: String? = ""
)