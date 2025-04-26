package com.rcll.core.api

interface IReducer<TState> {
    fun reduce(state: TState, action: IAction): TState
}