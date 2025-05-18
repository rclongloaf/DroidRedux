package com.rcll.domain.cache

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import kotlinx.collections.immutable.persistentHashMapOf

interface UsersCacheReducer : Reducer<UsersCache>

class UsersCacheReducerImpl : UsersCacheReducer {
    override fun reduce(state: UsersCache, action: Action): UsersCache {
        if (action !is UsersCacheAction) return state

        val newUsersMap = when (action) {
            is UsersCacheAction.Add -> state.usersMap.put(action.id, action.data)
            UsersCacheAction.Clear -> persistentHashMapOf()
            is UsersCacheAction.Remove -> state.usersMap.remove(action.id)
        }

        return state.smartCopy(
            usersMap = newUsersMap
        )
    }
}