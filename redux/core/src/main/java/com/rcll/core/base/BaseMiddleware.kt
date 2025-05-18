package com.rcll.core.base

import com.rcll.core.api.Action
import com.rcll.core.api.Middleware
import com.rcll.core.api.Reducer
import com.rcll.core.api.Store

abstract class BaseMiddleware<TState : Any> : Middleware<TState> {
    private var next: Reducer<TState>? = null
    protected var currentStore: Store<TState>? = null

    override fun setNextReducer(reducer: Reducer<TState>) {
        next = reducer
    }

    override fun setStore(store: Store<TState>) {
        this.currentStore = store
    }

    protected fun reduceNext(state: TState, action: Action): TState {
        return next?.reduce(state, action) ?: throw IllegalStateException("Next reducer is not set")
    }
}
