package com.rcll.core.base

import com.rcll.core.api.Action
import com.rcll.core.api.Middleware
import com.rcll.core.api.Reducer
import com.rcll.core.api.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseStore<TState : Any>(
    initialState: TState,
    middlewares: List<Middleware<TState>>,
    private val rootReducer: Reducer<TState>,
    override val scope: CoroutineScope
) : Store<TState> {

    protected val mutableStateFlow = MutableStateFlow(initialState)
    override val stateFlow = mutableStateFlow.asStateFlow()

    private var nextReducer: Reducer<TState> = rootReducer

    init {
        applyMiddlewares(middlewares)
    }

    override fun dispatch(action: Action) {
        scope.launch {
            mutableStateFlow.value = nextReducer.reduce(mutableStateFlow.value, action)
        }
    }

    private fun applyMiddlewares(middlewares: List<Middleware<TState>>) {
        nextReducer = rootReducer

        for (middleware in middlewares.reversed()) {
            middleware.setStore(this)
            middleware.setNextReducer(nextReducer)
            nextReducer = middleware
        }
    }
}
