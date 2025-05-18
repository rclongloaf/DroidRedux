package com.rcll.droidredux.feature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.rcll.core.base.StoreProviders
import com.rcll.core.composition.runBypassComposition
import com.rcll.droidredux.redux.AppStateFeature
import com.rcll.droidredux.redux.AppStore
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppFeatureComposition : KoinComponent {
    private val store: AppStore by inject()

    init {
        runBypassComposition(
            scope = store.scope,
            recomposeContext = Dispatchers.Default.limitedParallelism(1)
        ) {
            FeatureComposition()
        }
    }

    @Composable
    private fun FeatureComposition() {
        StoreProviders(store) {
            val state = store.stateFlow.collectAsState()

            AppStateFeature(state.value)
        }
    }
}