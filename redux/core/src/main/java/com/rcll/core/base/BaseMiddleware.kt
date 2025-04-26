package com.rcll.core.base

import com.rcll.core.api.IAction
import com.rcll.core.api.IMiddleware
import com.rcll.core.api.IReducer
import com.rcll.core.api.IStore

abstract class BaseMiddleware<TState : Any> : IMiddleware<TState> {
    private var next: IReducer<TState>? = null
    protected var currentStore: IStore<*>? = null

    override fun setNextReducer(reducer: IReducer<TState>) {
        next = reducer
    }

    override fun setStore(store: IStore<TState>) {
        this.currentStore = store
    }

    protected fun reduceNext(state: TState, action: IAction): TState {
        return next?.reduce(state, action) ?: throw IllegalStateException("Next reducer is not set")
    }
}
