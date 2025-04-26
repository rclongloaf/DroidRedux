package com.rcll.domain.provider

import com.rcll.core.api.IReducer
import com.rcll.core.middlewares.dynamic.DynamicMiddleware
import com.rcll.domain.cache.UsersCacheAction
import kotlin.reflect.KClass

class UsersProviderDynamicMiddleware : DynamicMiddleware<UsersProviderAction>() {
    override val actionClassFilter: KClass<UsersProviderAction> = UsersProviderAction::class

    override fun <TState> reduceBeforeNextReducer(
        state: TState,
        action: UsersProviderAction,
        newActionReducer: IReducer<TState>
    ): TState {
        return when (action) {
            is UsersProviderAction.OnFetchSuccess -> {
                newActionReducer.reduce(state, UsersCacheAction.Add(action.id, action.data))
            }

            is UsersProviderAction.Remove -> {
                newActionReducer.reduce(state, UsersCacheAction.Remove(action.id))
            }

            UsersProviderAction.Clear -> {
                newActionReducer.reduce(state, UsersCacheAction.Clear)
            }

            is UsersProviderAction.Fetch,
            is UsersProviderAction.OnFetchError -> state
        }
    }
}