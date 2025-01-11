package com.rcll.core.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.rcll.core.api.IStore

val LocalStore = compositionLocalOf<IStore<*>> { error("No class provide") }

@Composable
fun <TState> StoreProvider(
    store: IStore<TState>,
    content: @Composable IStore<TState>.() -> Unit
) {
    CompositionLocalProvider(LocalStore provides store) {
        store.content()
    }
}
