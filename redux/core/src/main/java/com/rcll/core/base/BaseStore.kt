package com.rcll.core.base

import com.rcll.core.api.IAction
import com.rcll.core.api.IDispatcher
import com.rcll.core.api.IMiddleware
import com.rcll.core.api.IPatch
import com.rcll.core.api.IStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseStore<TState>(
    initialState: TState,
    middlewares: List<IMiddleware>,
    private val rootReducer: (TState, IPatch) -> TState
) : IStore<TState> {

    protected val mutableStateFlow = MutableStateFlow<TState>(initialState)
    override val stateFlow = mutableStateFlow.asStateFlow()

    private val defaultDispatcher = object : IDispatcher {
        override fun dispatch(action: IAction) {
            dispatchReducer(action)
        }
    }
    private var nextDispatcher : IDispatcher = defaultDispatcher

    init {
        applyMiddlewares(middlewares)
    }

    override fun dispatch(action: IAction) {
        nextDispatcher.dispatch(action)
    }

    private fun dispatchReducer(action: IAction) {
        if (action is IPatch) {
            // todo batch patches
            val newState = rootReducer(mutableStateFlow.value, action)
            mutableStateFlow.value = newState
        }
    }

    private fun applyMiddlewares(middlewares: List<IMiddleware>) {
        nextDispatcher = defaultDispatcher

        for (middleware in middlewares.reversed()) {
            middleware.setStore(this)
            middleware.setNextDispatcher(nextDispatcher)
            nextDispatcher = middleware
        }
    }
}
