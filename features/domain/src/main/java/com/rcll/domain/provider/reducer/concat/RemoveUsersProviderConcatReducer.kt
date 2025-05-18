package com.rcll.domain.provider.reducer.concat

import com.rcll.core.api.Reducer
import com.rcll.core.middlewares.concat.filtered.FilteredConcatReducer
import com.rcll.domain.cache.UsersCacheAction
import com.rcll.domain.provider.UsersProviderAction

class RemoveUsersProviderConcatReducer : FilteredConcatReducer<UsersProviderAction.Remove>() {
    override val actionFilterClass = UsersProviderAction.Remove::class

    override fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        action: UsersProviderAction.Remove,
        newActionReducer: Reducer<TState>
    ): TState {
        return newActionReducer.reduce(state, UsersCacheAction.Remove(action.id))
    }
}