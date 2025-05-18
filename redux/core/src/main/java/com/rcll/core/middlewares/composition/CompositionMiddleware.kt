package com.rcll.core.middlewares.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import com.rcll.core.api.Action
import com.rcll.core.api.Dispatcher
import com.rcll.core.api.Store
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.composition.runBypassComposition
import com.rcll.core.dispatcher.ImmediateCoroutineDispatcher

abstract class CompositionMiddleware<TState : Any> : BaseMiddleware<TState>() {
    private var compositionState: MutableState<TState>? = null

    abstract fun getStateComposition(
        dispatcher: Dispatcher,
        state: State<TState>
    ): @Composable () -> Unit

    override fun setStore(store: Store<TState>) {
        super.setStore(store)

        val compositionState = mutableStateOf(store.stateFlow.value)
        this.compositionState = compositionState

        runBypassComposition(
            scope = store.scope,
            recomposeContext = ImmediateCoroutineDispatcher,
            compositionContent = getStateComposition(store, compositionState)
        )
    }

    override fun reduce(state: TState, action: Action): TState {
        val newState = reduceNext(state, action)

        applyAndRecomposeNewState(newState)

        return newState
    }

    private fun applyAndRecomposeNewState(newState: TState) {
        val compositionState = checkNotNull(this.compositionState) {
            "Store must be set before reduce"
        }

        Snapshot.withMutableSnapshot {
            /**
             * Создаём изолированный снепшот, чтобы потокобезопасно применить новое состояние
             * и вызвать рекомпозицию реактивно в том же потоке без диспатчинга.
             * Реактивность рекомпозиции гарантируется наличием [ImmediateCoroutineDispatcher]
             * в контексте активной рекомпозиции.
             */
            compositionState.value = newState
        }
    }
}