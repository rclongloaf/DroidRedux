package com.rcll.core.middlewares.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import com.rcll.core.api.IAction
import com.rcll.core.api.IDispatcher
import com.rcll.core.api.IStore
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.composition.BasicApplier
import com.rcll.core.dispatcher.ImmediateCoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

abstract class CompositionMiddleware<TState : Any> : BaseMiddleware<TState>() {
    private var compositionState: MutableState<TState>? = null

    abstract fun getStateComposition(
        dispatcher: IDispatcher,
        state: State<TState>
    ): @Composable () -> Unit

    override fun setStore(store: IStore<TState>) {
        super.setStore(store)

        val compositionState = mutableStateOf(store.stateFlow.value)
        this.compositionState = compositionState

        initComposition(store.scope, getStateComposition(store, compositionState))
    }

    private fun initComposition(
        scope: CoroutineScope,
        stateComposition: @Composable () -> Unit
    ) {
        val recomposerContext = ImmediateCoroutineDispatcher + FallbackFrameClock

        /**
         * effectCoroutineContext - контекст, в котором будут запускаться LaunchedEffect-ы.
         */
        val recomposer = Recomposer(effectCoroutineContext = Dispatchers.Default)
        val composition = Composition(BasicApplier(), recomposer)

        scope.launch(context = recomposerContext, start = CoroutineStart.UNDISPATCHED) {
            try {
                recomposer.runRecomposeAndApplyChanges()
            } catch (e: CancellationException) {
                composition.dispose()
            }
        }

        composition.setContent(stateComposition)
    }

    override fun reduce(state: TState, action: IAction): TState {
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

    /**
     * Синхронная реализация [MonotonicFrameClock], которая позволяет сразу запускать рекомпозицию
     * при уведомлении об изменениях читаемых [State]-ов.
     */
    private object FallbackFrameClock : MonotonicFrameClock {
        override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R {
            return onFrame(System.nanoTime())
        }
    }
}