package com.rcll.core.api

import com.rcll.core.middlewares.concat.ActionConsumer

interface Middleware<TState : Any> : ActionConsumer<TState> {
    fun setNextMiddleware(middleware: Middleware<TState>)
    suspend fun consumeAsync(state: TState, action: Action)
}
