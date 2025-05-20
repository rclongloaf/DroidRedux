package com.rcll.domain.provider.reducer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.domain.provider.MutableUsersProvider
import com.rcll.domain.provider.UsersProvider
import com.rcll.domain.provider.UsersProviderAction
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentHashSetOf

interface UsersProviderReducer : Reducer<MutableUsersProvider>

class UsersProviderReducerImpl : UsersProviderReducer {
    override fun reduce(state: MutableUsersProvider, action: Action): MutableUsersProvider {
        if (action !is UsersProviderAction) return state

        var fetchingStatusSet by state.fetchingStatusSet
        var errorStatusMap by state.errorStatusMap

        when (action) {
            UsersProviderAction.Clear -> {
                fetchingStatusSet = persistentHashSetOf()
                errorStatusMap = persistentHashMapOf()
            }

            is UsersProviderAction.Fetch -> {
                fetchingStatusSet = fetchingStatusSet.add(action.id)
            }

            is UsersProviderAction.OnFetchError -> {
                fetchingStatusSet = fetchingStatusSet.remove(action.id)
                errorStatusMap = errorStatusMap.put(action.id, action.error)
            }

            is UsersProviderAction.OnFetchSuccess -> {
                fetchingStatusSet = fetchingStatusSet.remove(action.id)
            }

            is UsersProviderAction.Remove -> {
                fetchingStatusSet = fetchingStatusSet.remove(action.id)
                errorStatusMap = errorStatusMap.remove(action.id)
            }
        }

        return state
    }
}