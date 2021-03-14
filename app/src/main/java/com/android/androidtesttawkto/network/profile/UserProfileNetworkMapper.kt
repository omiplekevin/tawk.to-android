package com.android.androidtesttawkto.network.profile

import com.android.androidtesttawkto.models.UserProfileModel
import com.android.androidtesttawkto.util.EntityMapper
import javax.inject.Inject

class UserProfileNetworkMapper @Inject constructor() :
    EntityMapper<UserProfileNetworkEntity, UserProfileModel> {

    override fun mapFromEntity(entity: UserProfileNetworkEntity): UserProfileModel {
        return UserProfileModel(
            id = entity.id,
            avatar_url = entity.avatar_url,
            bio = entity.bio,
            blog = entity.blog,
            company = entity.company,
            created_at = entity.created_at,
            email = entity.email,
            events_url = entity.events_url,
            followers = entity.followers,
            followers_url = entity.followers_url,
            following = entity.following,
            following_url = entity.following_url,
            gists_url = entity.gists_url,
            gravatar_id = entity.gravatar_id,
            hireable = entity.hireable,
            html_url = entity.html_url,
            location = entity.location,
            login = entity.login,
            name = entity.name,
            node_id = entity.node_id,
            organizations_url = entity.organizations_url,
            public_gists = entity.public_gists,
            public_repos = entity.public_repos,
            received_events_url = entity.received_events_url,
            repos_url = entity.repos_url,
            site_admin = entity.site_admin,
            starred_url = entity.starred_url,
            subscriptions_url = entity.subscriptions_url,
            twitter_username = entity.twitter_username,
            type = entity.type,
            updated_at = entity.updated_at,
            url = entity.url
        )
    }

    override fun mapToEntity(domainModel: UserProfileModel): UserProfileNetworkEntity {
        return UserProfileNetworkEntity(
            id = domainModel.id,
            avatar_url = domainModel.avatar_url,
            bio = domainModel.bio,
            blog = domainModel.blog,
            company = domainModel.company,
            created_at = domainModel.created_at,
            email = domainModel.email,
            events_url = domainModel.events_url,
            followers = domainModel.followers,
            followers_url = domainModel.followers_url,
            following = domainModel.following,
            following_url = domainModel.following_url,
            gists_url = domainModel.gists_url,
            gravatar_id = domainModel.gravatar_id,
            hireable = domainModel.hireable,
            html_url = domainModel.html_url,
            location = domainModel.location,
            login = domainModel.login,
            name = domainModel.name,
            node_id = domainModel.node_id,
            organizations_url = domainModel.organizations_url,
            public_gists = domainModel.public_gists,
            public_repos = domainModel.public_repos,
            received_events_url = domainModel.received_events_url,
            repos_url = domainModel.repos_url,
            site_admin = domainModel.site_admin,
            starred_url = domainModel.starred_url,
            subscriptions_url = domainModel.subscriptions_url,
            twitter_username = domainModel.twitter_username,
            type = domainModel.type,
            updated_at = domainModel.updated_at,
            url = domainModel.url
        )
    }
}