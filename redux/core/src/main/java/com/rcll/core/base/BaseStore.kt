package com.rcll.core.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rcll.core.api.Action
import com.rcll.core.api.Middleware
import com.rcll.core.api.Reducer
import com.rcll.core.api.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseStore<TState : Any>(
    initialState: TState,
    middlewares: List<Middleware<TState>>,
    private val rootReducer: Reducer<TState>,
    override val scope: CoroutineScope
) : Store<TState> {

    protected val mutableState = mutableStateOf(initialState)
    override val state: State<TState> = mutableState

    private val reducerMiddleware = ReducerMiddleware()
    private var nextMiddleware: Middleware<TState> = reducerMiddleware

    init {
        applyMiddlewares(middlewares)
    }

    override fun dispatch(action: Action) {
        scope.launch {
            /**
             * Игнорируем результат, т.к. корневой объект не должен изменяться
             * для синхронной работы снепшотов.
             * Всё что может измениться в корневом объекте требуется обернуть в MutableState
             */
            nextMiddleware.consumeAsync(mutableState.value, action)
        }
    }

    private fun applyMiddlewares(middlewares: List<Middleware<TState>>) {
        nextMiddleware = reducerMiddleware

        for (middleware in middlewares.reversed()) {
            middleware.setNextMiddleware(nextMiddleware)
            nextMiddleware = middleware
        }
    }

    inner class ReducerMiddleware : Middleware<TState> {
        override fun setNextMiddleware(middleware: Middleware<TState>) {
            throw IllegalStateException("Reducer is last middleware")
        }

        override suspend fun consumeAsync(state: TState, action: Action) {
            consume(state, action)
        }

        override fun consume(state: TState, action: Action) {
            mutableState.value = rootReducer.reduce(state, action)
        }
    }
}
