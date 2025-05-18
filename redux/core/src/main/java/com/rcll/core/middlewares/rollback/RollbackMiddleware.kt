package com.rcll.core.middlewares.rollback

import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.error.FatalTransactionException
import com.rcll.core.error.TransactionException

class RollbackMiddleware<TState : Any> : BaseMiddleware<TState>() {
    override fun reduce(state: TState, action: Action): TState {
        var newState = state
        try {
            newState = reduceNext(state, action)
        } catch(e: FatalTransactionException) {
            // todo log exception and throw it
            throw e
        } catch (e: TransactionException) {
            // todo log exception and ignore it
        }

        return newState
    }
}