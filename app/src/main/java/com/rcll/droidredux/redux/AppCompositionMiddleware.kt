package com.rcll.droidredux.redux

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.rcll.core.api.Dispatcher
import com.rcll.core.base.StoreDispatcherProvider
import com.rcll.core.base.StoreStateProvider
import com.rcll.core.middlewares.composition.CompositionMiddleware
import com.rcll.core.middlewares.dynamic.DynamicMiddlewareManagerProvider
import com.rcll.core.middlewares.dynamic.manager.DynamicActionObserversManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Deprecated("Use AppFeatureComposition instead")
class AppCompositionMiddleware : CompositionMiddleware<AppState>(), KoinComponent {
    private val dynamicActionObserversManager: DynamicActionObserversManager by inject()

    override fun getStateComposition(
        dispatcher: Dispatcher,
        state: State<AppState>
    ) = @Composable {
        StoreStateProvider(state) {
            StoreDispatcherProvider(dispatcher) {
                DynamicMiddlewareManagerProvider(dynamicActionObserversManager) {
                    AppStateFeature(state.value)
                }
            }
        }
    }
}