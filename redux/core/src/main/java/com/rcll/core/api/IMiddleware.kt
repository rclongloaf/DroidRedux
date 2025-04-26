package com.rcll.core.api

interface IMiddleware<TState : Any> : IReducer<TState> {
    fun setNextReducer(reducer: IReducer<TState>)
    fun setStore(store: IStore<TState>)
}
