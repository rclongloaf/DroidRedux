package com.rcll.core.api

interface Middleware<TState : Any> : Reducer<TState> {
    fun setNextReducer(reducer: Reducer<TState>)
    fun setStore(store: Store<TState>)
}
