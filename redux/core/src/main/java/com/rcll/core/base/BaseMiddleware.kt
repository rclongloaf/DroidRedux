package com.rcll.core.base

import com.rcll.core.api.Action
import com.rcll.core.api.Middleware

abstract class BaseMiddleware<TState : Any> : Middleware<TState> {
    private var next: Middleware<TState>? = null

    override fun setNextMiddleware(middleware: Middleware<TState>) {
        next = middleware
    }

    protected suspend fun consumeNextAsync(state: TState, action: Action) {
        return next?.consumeAsync(state, action) ?: throw IllegalStateException("Next reducer is not set")
    }

    protected fun consumeNext(state: TState, action: Action) {
        return next?.consume(state, action) ?: throw IllegalStateException("Next reducer is not set")
    }
}
