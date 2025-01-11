package com.rcll.droidredux

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.collectAsState
import com.rcll.core.api.IStore
import com.rcll.core.base.StoreProvider
import com.rcll.core.composition.BasicApplier
import com.rcll.droidredux.redux.AppState
import com.rcll.droidredux.redux.AppStateHandler
import com.rcll.droidredux.redux.AppStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

lateinit var store: IStore<AppState>

class MyApplication : Application() {
    @OptIn(ExperimentalComposeApi::class)
    override fun onCreate() {
        super.onCreate()

        store = AppStore()

        val compositionContext = SupervisorJob() + Main
        initComposition(compositionContext) {
            StoreProvider(store) {
                val state = store.stateFlow.collectAsState()
                AppStateHandler(state.value)
            }
        }
    }
}

private fun initComposition(
    context: CoroutineContext,
    content: @Composable () -> Unit
) {
    val finalContext = context + FallbackFrameClock

    val recomposer = Recomposer(finalContext)
    val composition = Composition(BasicApplier(), recomposer)

    CoroutineScope(finalContext).launch(start = CoroutineStart.UNDISPATCHED) {
        try {
            recomposer.runRecomposeAndApplyChanges()
        } catch (e: CancellationException) {
            composition.dispose()
        }
    }

    composition.setContent(content)
}

private object FallbackFrameClock : MonotonicFrameClock {
    override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R =
        withContext(Main) {
            store.stateFlow.collect {
                onFrame(System.nanoTime())
            }
        }
}
