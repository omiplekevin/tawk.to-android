package com.android.androidtesttawkto.room.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserCacheEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String?,

    @ColumnInfo(name = "events_url")
    var events_url: String?,

    @ColumnInfo(name = "followers_url")
    var followers_url: String?,

    @ColumnInfo(name = "following_url")
    var following_url: String?,

    @ColumnInfo(name = "gists_url")
    var gists_url: String?,

    @ColumnInfo(name = "gravatar_id")
    var gravatar_id: String?,

    @ColumnInfo(name = "html_url")
    var html_url: String?,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "node_id")
    var node_id: String?,

    @ColumnInfo(name = "organizations_url")
    var organizations_url: String?,

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

    @ColumnInfo(name = "type")
    var type: String?,

    @ColumnInfo(name = "url")
    var url: String?,

    @ColumnInfo(name = "hasNotes", defaultValue = "false")
    var hasNotes: Boolean
)