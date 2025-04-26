package com.rcll.domain.provider

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentHashSetOf

interface UsersProviderReducer : IReducer<UsersProvider>

class UsersProviderReducerImpl : UsersProviderReducer {
    override fun reduce(state: UsersProvider, action: IAction): UsersProvider {
        if (action !is UsersProviderAction) return state

        var fetchingStatusSet = state.fetchingStatusSet
        var errorStatusMap = state.errorStatusMap

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

        return state.smartCopy(
            fetchingStatusSet = fetchingStatusSet,
            errorStatusMap = errorStatusMap,
        )
    }
}