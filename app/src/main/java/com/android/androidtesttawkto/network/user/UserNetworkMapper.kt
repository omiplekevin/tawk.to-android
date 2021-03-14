package com.android.androidtesttawkto.network.user

import com.android.androidtesttawkto.models.UserModel
import com.android.androidtesttawkto.util.EntityMapper
import javax.inject.Inject

class UserNetworkMapper @Inject constructor(): EntityMapper<UserNetworkEntity, UserModel> {

    override fun mapFromEntity(entity: UserNetworkEntity): UserModel {
        return UserModel(
            avatar_url = entity.avatar_url,
            events_url = entity.events_url,
            followers_url = entity.followers_url,
            following_url = entity.following_url,
            gists_url = entity.gists_url,
            gravatar_id = entity.gravatar_id,
            html_url = entity.html_url,
            id = entity.id,
            login = entity.login,
            node_id = entity.node_id,
            organizations_url = entity.organizations_url,
            received_events_url = entity.received_events_url,
            repos_url = entity.repos_url,
            site_admin = entity.site_admin,
            starred_url = entity.starred_url,
            subscriptions_url = entity.subscriptions_url,
            type = entity.type,
            url = entity.url
        )
    }

    override fun mapToEntity(domainModel: UserModel): UserNetworkEntity {
        return UserNetworkEntity(
            avatar_url = domainModel.avatar_url,
            events_url = domainModel.events_url,
            followers_url = domainModel.followers_url,
            following_url = domainModel.following_url,
            gists_url = domainModel.gists_url,
            gravatar_id = domainModel.gravatar_id,
            html_url = domainModel.html_url,
            id = domainModel.id,
            login = domainModel.login,
            node_id = domainModel.node_id,
            organizations_url = domainModel.organizations_url,
            received_events_url = domainModel.received_events_url,
            repos_url = domainModel.repos_url,
            site_admin = domainModel.site_admin,
            starred_url = domainModel.starred_url,
            subscriptions_url = domainModel.subscriptions_url,
            type = domainModel.type,
            url = domainModel.url
        )
    }

    fun mapFromEntityList(entities: List<UserNetworkEntity>): List<UserModel> {
        return entities.map { mapFromEntity(it) }
    }
}