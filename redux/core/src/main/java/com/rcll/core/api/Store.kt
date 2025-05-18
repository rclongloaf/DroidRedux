package com.rcll.core.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface Store<TState : Any> : Dispatcher {
    /**
     * Поток состояния стора.
     */
    val stateFlow: StateFlow<TState>

    /**
     * Cкоуп обработки экшенов. Должен иметь singleThread реализацию.
     */
    val scope: CoroutineScope
}
