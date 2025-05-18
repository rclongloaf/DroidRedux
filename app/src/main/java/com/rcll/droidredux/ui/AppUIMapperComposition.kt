package com.rcll.droidredux.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.rcll.core.base.StoreProviders
import com.rcll.core.composition.runBypassComposition
import com.rcll.droidredux.redux.AppStore
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppUIMapperComposition(
    private val appUIStateProvider: AppUIStateProvider
) : KoinComponent {
    private val store: AppStore by inject()

    init {
        runBypassComposition(
            scope = store.scope,
            recomposeContext = Dispatchers.Default.limitedParallelism(1)
        ) {
            UIStateComposition()
        }
    }

    @Composable
    private fun UIStateComposition() {
        StoreProviders(store) {
            val state = store.stateFlow.collectAsState()

            AppUIMapper(appUIStateProvider.mutableUIState, state.value)
        }
    }
}