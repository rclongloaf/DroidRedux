package com.rcll.core.api

import kotlinx.coroutines.flow.StateFlow

interface IStore<TState> : IDispatcher {
    val stateFlow : StateFlow<TState>
}
