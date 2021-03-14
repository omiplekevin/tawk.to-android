package com.android.androidtesttawkto.network.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserProfileNetworkEntity (

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("avatar_url")
    @Expose
    val avatar_url: String?,

    @SerializedName("bio")
    @Expose
    val bio: String?,

    @SerializedName("blog")
    @Expose
    val blog: String?,

    @SerializedName("company")
    @Expose
    val company: String?,

    @SerializedName("created_at")
    @Expose
    val created_at: String?,

    @SerializedName("email")
    @Expose
    val email: String?,

    @SerializedName("events_url")
    @Expose
    val events_url: String?,

    @SerializedName("followers")
    @Expose
    val followers: Int,

    @SerializedName("followers_url")
    @Expose
    val followers_url: String?,

    @SerializedName("following")
    @Expose
    val following: Int,

    @SerializedName("following_url")
    @Expose
    val following_url: String?,

    @SerializedName("gists_url")
    @Expose
    val gists_url: String?,

    @SerializedName("gravatar_id")
    @Expose
    val gravatar_id: String?,

    @SerializedName("hireable")
    @Expose
    val hireable: String?,

    @SerializedName("html_url")
    @Expose
    val html_url: String?,

    @SerializedName("location")
    @Expose
    val location: String?,

    @SerializedName("login")
    @Expose
    val login: String?,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("node_id")
    @Expose
    val node_id: String?,

    @SerializedName("organizations_url")
    @Expose
    val organizations_url: String?,

    @SerializedName("public_gists")
    @Expose
    val public_gists: Int,

    @SerializedName("public_repos")
    @Expose
    val public_repos: Int,

    @SerializedName("received_events_url")
    @Expose
    val received_events_url: String?,

    @SerializedName("repos_url")
    @Expose
    val repos_url: String?,

    @SerializedName("site_admin")
    @Expose
    val site_admin: Boolean,

    @SerializedName("starred_url")
    @Expose
    val starred_url: String?,

    @SerializedName("subscriptions_url")
    @Expose
    val subscriptions_url: String?,

    @SerializedName("twitter_username")
    @Expose
    val twitter_username: String?,

    @SerializedName("type")
    @Expose
    val type: String?,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String?,

    @SerializedName("url")
    @Expose
    val url: String?
)