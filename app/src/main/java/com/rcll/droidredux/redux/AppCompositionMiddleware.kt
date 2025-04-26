package com.rcll.droidredux.redux

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.rcll.core.api.IDispatcher
import com.rcll.core.base.StoreDispatcherProvider
import com.rcll.core.base.StoreStateProvider
import com.rcll.core.middlewares.composition.CompositionMiddleware
import com.rcll.core.middlewares.dynamic.DynamicMiddlewareManagerProvider
import com.rcll.core.middlewares.dynamic.holder.DynamicMiddlewaresHolder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppCompositionMiddleware : CompositionMiddleware<AppState>(), KoinComponent {
    private val dynamicMiddlewaresHolder: DynamicMiddlewaresHolder by inject()

    override fun getStateComposition(
        dispatcher: IDispatcher,
        state: State<AppState>
    ) = @Composable {
        StoreStateProvider(state) {
            StoreDispatcherProvider(dispatcher) {
                DynamicMiddlewareManagerProvider(dynamicMiddlewaresHolder) {
                    AppStateFeature(state.value)
                }
            }
        }
    }
}