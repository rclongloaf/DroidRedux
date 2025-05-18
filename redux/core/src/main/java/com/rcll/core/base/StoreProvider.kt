package com.rcll.core.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import com.rcll.core.api.Dispatcher
import com.rcll.core.api.Store

val LocalStoreDispatcher = compositionLocalOf<Dispatcher> { error("No dispatcher provide") }
val LocalStoreState = compositionLocalOf<Any> { error("No state provide") }

@Composable
inline fun StoreProviders(
    store: Store<*>,
    crossinline content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalStoreDispatcher provides store,
        LocalStoreState provides store,
    ) {
        content()
    }
}

@Composable
inline fun StoreDispatcherProvider(
    dispatcher: Dispatcher,
    crossinline content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalStoreDispatcher provides dispatcher) {
        content()
    }
}

@Composable
inline fun <TState : Any> StoreStateProvider(
    state: State<TState>,
    crossinline content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalStoreState provides state) {
        content()
    }
}
