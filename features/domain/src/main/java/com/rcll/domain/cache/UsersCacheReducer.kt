package com.rcll.domain.cache

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.core.error.notConsistentState
import kotlinx.collections.immutable.persistentHashMapOf

interface UsersCacheReducer : Reducer<MutableUsersCache>

class UsersCacheReducerImpl : UsersCacheReducer {
    override fun reduce(state: MutableUsersCache, action: Action): MutableUsersCache {
        if (action !is UsersCacheAction) return state

        var usersMap by state.usersMap

        when (action) {
            is UsersCacheAction.Add -> {
                if (usersMap.containsKey(action.id)) {
                    notConsistentState(
                        message = "UserId=${action.id} already contains. Use UsersCacheAction.Update instead"
                    )
                }
                usersMap = usersMap.put(action.id, mutableStateOf(action.data))
            }
            is UsersCacheAction.Update -> {
                usersMap[action.id]?.let {
                    it.value = action.data
                } ?: notConsistentState(
                    message = "UserId=${action.id} not exists"
                )
            }
            UsersCacheAction.Clear -> {
                usersMap = persistentHashMapOf()
            }
            is UsersCacheAction.Remove -> {
                usersMap = usersMap.remove(action.id)
            }
        }

        return state
    }
}