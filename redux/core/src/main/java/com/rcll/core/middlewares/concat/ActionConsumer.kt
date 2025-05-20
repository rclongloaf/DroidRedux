package com.rcll.core.middlewares.concat

import com.rcll.core.api.Action

interface ActionConsumer<TState> {
    fun consume(state: TState, action: Action)
}