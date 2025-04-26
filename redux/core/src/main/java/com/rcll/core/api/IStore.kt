package com.rcll.core.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface IStore<TState : Any> : IDispatcher {
    /**
     * Поток состояния стора.
     */
    val stateFlow: StateFlow<TState>

    /**
     * Cкоуп обработки экшенов. Должен иметь singleThread реализацию.
     */
    val scope: CoroutineScope
}
