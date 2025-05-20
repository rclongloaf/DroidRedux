package com.rcll.domain.provider.reducer.concat

import com.rcll.core.middlewares.concat.ActionConsumer
import com.rcll.core.middlewares.concat.filtered.FilteredConcatReducer
import com.rcll.domain.cache.UsersCacheAction
import com.rcll.domain.provider.UsersProviderAction

class RemoveUsersProviderConcatReducer : FilteredConcatReducer<UsersProviderAction.Remove>() {
    override val actionFilterClass = UsersProviderAction.Remove::class

    override fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        action: UsersProviderAction.Remove,
        newActionConsumer: ActionConsumer<TState>
    ) {
        newActionConsumer.consume(state, UsersCacheAction.Remove(action.id))
    }
}