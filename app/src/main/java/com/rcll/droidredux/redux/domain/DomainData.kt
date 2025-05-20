package com.rcll.droidredux.redux.domain

import androidx.compose.runtime.Stable
import com.rcll.domain.cache.MutableUsersCache
import com.rcll.domain.cache.UsersCache

@Stable
interface DomainData {
    val usersCache: UsersCache
}

data class MutableDomainData(
    override val usersCache: MutableUsersCache = MutableUsersCache()
) : DomainData {

    fun smartCopy(
        usersCache: MutableUsersCache
    ): MutableDomainData {
        if (usersCache !== this.usersCache) {
            return MutableDomainData(
                usersCache = usersCache
            )
        }
        return this
    }
}