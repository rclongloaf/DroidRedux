package com.rcll.droidredux.redux.entities.providers.users

import com.rcll.domain.cache.UsersCacheSelector
import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId
import com.rcll.domain.provider.UserRequestError
import com.rcll.domain.provider.UsersProvider
import com.rcll.domain.provider.UsersProviderSelector
import com.rcll.domain.status.StatusLCER
import com.rcll.droidredux.redux.entities.EntitiesSelector
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UsersProviderSelectorImpl : UsersProviderSelector, KoinComponent {
    private val entitiesSelector: EntitiesSelector by inject()
    private val usersCacheSelector: UsersCacheSelector by inject()

    override fun isFetching(state: Any, id: UserId): Boolean {
        val usersProvider = getProvider(state)

        return usersProvider.fetchingStatusSet.contains(id)
    }

    override fun hasError(state: Any, id: UserId): Boolean {
        val usersProvider = getProvider(state)

        return usersProvider.errorStatusMap.containsKey(id)
    }

    override fun getStatusLCER(state: Any, id: UserId): StatusLCER<UserData, UserRequestError> {
        val userProvider = getProvider(state)

        val cachedData = usersCacheSelector.getOrNull(state, id)
        val isFetching = userProvider.fetchingStatusSet.contains(id)
        val requestError = userProvider.errorStatusMap[id]

        return when {
            cachedData != null && isFetching -> StatusLCER.ContentWithLoading(cachedData)
            cachedData != null && requestError != null -> StatusLCER.ContentWithError(
                cachedData,
                requestError
            )

            cachedData != null -> StatusLCER.Content(cachedData)
            isFetching -> StatusLCER.Loading
            requestError != null -> StatusLCER.Error(requestError)
            else -> error("Illegal status combination")
        }
    }

    private fun getProvider(state: Any): UsersProvider {
        return entitiesSelector.get(state).providers.usersProvider
    }

}