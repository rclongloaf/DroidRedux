package com.rcll.core.middlewares

import com.rcll.core.api.IAction
import com.rcll.core.api.IRunnableAction
import com.rcll.core.base.BaseMiddleware

class ThunkMiddleware<TState : Any> : BaseMiddleware<TState>() {
    override fun reduce(state: TState, action: IAction): TState {
        val currentStore = currentStore ?: throw IllegalStateException("CurrentStore is not set")

        if (action is IRunnableAction) {
            action.run(currentStore)
        }

        return reduceNext(state, action)
    }
}
