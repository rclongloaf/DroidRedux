package com.rcll.core.api

interface IMiddleware : IDispatcher {
    fun setNextDispatcher(dispatcher: IDispatcher)
    fun setStore(store: IStore<*>)
}
