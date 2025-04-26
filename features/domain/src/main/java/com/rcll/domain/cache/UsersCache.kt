package com.rcll.domain.cache

import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf

data class UsersCache(
    val usersMap: PersistentMap<UserId, UserData> = persistentHashMapOf()
) {
    fun smartCopy(
        usersMap: PersistentMap<UserId, UserData>
    ): UsersCache {
        if (usersMap !== this.usersMap) {
            return UsersCache(usersMap)
        }
        return this
    }
}