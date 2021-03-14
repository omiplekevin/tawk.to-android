package com.android.androidtesttawkto.network.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserNetworkEntity (

    @SerializedName("avatar_url")
    @Expose
    val avatar_url: String = "",

    @SerializedName("events_url")
    @Expose
    val events_url: String = "",

    @SerializedName("followers_url")
    @Expose
    val followers_url: String = "",

    @SerializedName("following_url")
    @Expose
    val following_url: String = "",

    @SerializedName("gists_url")
    @Expose
    val gists_url: String = "",

    @SerializedName("gravatar_id")
    @Expose
    val gravatar_id: String = "",

    @SerializedName("html_url")
    @Expose
    val html_url: String = "",

    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("login")
    @Expose
    val login: String = "",

    @SerializedName("node_id")
    @Expose
    val node_id: String = "",

    @SerializedName("organizations_url")
    @Expose
    val organizations_url: String = "",

    @SerializedName("received_events_url")
    @Expose
    val received_events_url: String = "",

    @SerializedName("repos_url")
    @Expose
    val repos_url: String = "",

    @SerializedName("site_admin")
    @Expose
    val site_admin: Boolean = false,

    @SerializedName("starred_url")
    @Expose
    val starred_url: String = "",

    @SerializedName("subscriptions_url")
    @Expose
    val subscriptions_url: String = "",

    @SerializedName("type")
    @Expose
    val type: String = "",

    @SerializedName("url")
    @Expose
    val url: String = ""
)