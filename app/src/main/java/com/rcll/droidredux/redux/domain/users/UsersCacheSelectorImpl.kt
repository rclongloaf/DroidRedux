package com.rcll.droidredux.redux.domain.users

import com.rcll.domain.cache.UsersCacheSelector
import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId
import com.rcll.droidredux.redux.domain.DomainSelector
import kotlinx.collections.immutable.PersistentMap
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UsersCacheSelectorImpl : UsersCacheSelector, KoinComponent {
    private val domainSelector: DomainSelector by inject()

    override fun contains(state: Any, id: UserId): Boolean {
        return getUsers(state).contains(id)
    }

    override fun get(state: Any, id: UserId): UserData {
        return getUsers(state).getValue(id)
    }

    override fun getOrNull(state: Any, id: UserId): UserData? {
        return getUsers(state)[id]
    }

    private fun getUsers(state: Any): PersistentMap<UserId, UserData> =
        domainSelector.get(state).usersCache.usersMap
}