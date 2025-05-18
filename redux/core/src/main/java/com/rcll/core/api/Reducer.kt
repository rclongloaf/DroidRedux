package com.rcll.core.api

interface Reducer<TState : Any> {
    fun reduce(state: TState, action: Action): TState
}