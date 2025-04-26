package com.rcll.domain.cache

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import kotlinx.collections.immutable.persistentHashMapOf

interface UsersCacheReducer : IReducer<UsersCache>

class UsersCacheReducerImpl : UsersCacheReducer {
    override fun reduce(state: UsersCache, action: IAction): UsersCache {
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