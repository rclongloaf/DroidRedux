package com.rcll.domain.provider.reducer.concat

import com.rcll.core.api.Reducer
import com.rcll.core.error.notConsistentState
import com.rcll.core.middlewares.concat.filtered.FilteredConcatReducer
import com.rcll.domain.cache.UsersCacheAction
import com.rcll.domain.provider.UsersProviderAction
import com.rcll.domain.provider.UsersProviderSelector
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnFetchSuccessUsersProviderConcatReducer
    : FilteredConcatReducer<UsersProviderAction.OnFetchSuccess>(), KoinComponent {

    private val usersProviderSelector: UsersProviderSelector by inject()

    override val actionFilterClass = UsersProviderAction.OnFetchSuccess::class

    override fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        action: UsersProviderAction.OnFetchSuccess,
        newActionReducer: Reducer<TState>
    ): TState {
        if (!usersProviderSelector.isFetching(state, action.id)) {
            notConsistentState("Fetching user not exists")
        }

        return newActionReducer.reduce(state, UsersCacheAction.Add(action.id, action.data))
    }
}