package com.rcll.core.base

import com.rcll.core.api.IAction
import com.rcll.core.api.IMiddleware
import com.rcll.core.api.IReducer
import com.rcll.core.api.IStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseStore<TState : Any>(
    initialState: TState,
    middlewares: List<IMiddleware<TState>>,
    private val rootReducer: IReducer<TState>,
    override val scope: CoroutineScope
) : IStore<TState> {

    protected val mutableStateFlow = MutableStateFlow(initialState)
    override val stateFlow = mutableStateFlow.asStateFlow()

    private var nextReducer: IReducer<TState> = rootReducer

    init {
        applyMiddlewares(middlewares)
    }

    override fun dispatch(action: IAction) {
        scope.launch {
            mutableStateFlow.value = nextReducer.reduce(mutableStateFlow.value, action)
        }
    }

    private fun applyMiddlewares(middlewares: List<IMiddleware<TState>>) {
        nextReducer = rootReducer

        for (middleware in middlewares.reversed()) {
            middleware.setStore(this)
            middleware.setNextReducer(nextReducer)
            nextReducer = middleware
        }
    }
}
