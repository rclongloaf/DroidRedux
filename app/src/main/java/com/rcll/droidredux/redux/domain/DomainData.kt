package com.rcll.droidredux.redux.domain

import com.rcll.domain.cache.UsersCache

data class DomainData(
    val usersCache: UsersCache = UsersCache(),
) {
    fun smartCopy(
        usersCache: UsersCache
    ): DomainData {
        if (usersCache !== this.usersCache) {
            return DomainData(
                usersCache = usersCache
            )
        }
        return this
    }
}