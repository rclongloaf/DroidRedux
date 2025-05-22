package com.rcll.core.middlewares.dynamic

import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware

class DynamicDeferredMiddleware<TState : Any>(
    private val deferredDynamicActionsHolder: DeferredDynamicActionsHolder
) : BaseMiddleware<TState>() {
    override fun reduce(state: TState, action: Action): TState {
        var newState = state

        try {
            newState = reduceNext(newState, action)
            deferredDynamicActionsHolder.processDeferredActions()
        } catch (e: Throwable) {
            throw e
        } finally {
            deferredDynamicActionsHolder.clearDeferredActions()
        }

        return newState
    }
}
