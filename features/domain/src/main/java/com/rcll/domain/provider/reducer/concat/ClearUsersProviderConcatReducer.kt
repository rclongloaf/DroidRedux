package com.rcll.domain.provider.reducer.concat

import com.rcll.core.middlewares.concat.ActionConsumer
import com.rcll.core.middlewares.concat.filtered.FilteredConcatReducer
import com.rcll.domain.cache.UsersCacheAction
import com.rcll.domain.provider.UsersProviderAction

class ClearUsersProviderConcatReducer : FilteredConcatReducer<UsersProviderAction.Clear>() {
    override val actionFilterClass = UsersProviderAction.Clear::class

    override fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        action: UsersProviderAction.Clear,
        newActionConsumer: ActionConsumer<TState>
    ) {
        newActionConsumer.consume(state, UsersCacheAction.Clear)
    }
}