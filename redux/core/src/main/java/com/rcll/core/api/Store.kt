package com.rcll.core.api

import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope

interface Store<TState : Any> : Dispatcher {
    /**
     * Поток состояния стора.
     */
    val state: State<TState>

    /**
     * Cкоуп обработки экшенов. Должен иметь singleThread реализацию.
     */
    val scope: CoroutineScope
}
