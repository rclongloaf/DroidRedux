package com.rcll.core.base

import com.rcll.core.api.IDispatcher
import com.rcll.core.api.IMiddleware
import com.rcll.core.api.IStore

abstract class BaseMiddleware : IMiddleware {
    protected var next: IDispatcher? = null
    protected var currentStore: IStore<*>? = null

    override fun setNextDispatcher(dispatcher: IDispatcher) {
        next = dispatcher
    }

    override fun setStore(store: IStore<*>) {
        this.currentStore = store
    }
}
