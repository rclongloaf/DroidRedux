package com.rcll.droidredux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.rcll.core.base.StoreDispatcherProvider
import com.rcll.core.base.StoreStateProvider
import com.rcll.core.middlewares.dynamic.DynamicMiddlewareManagerProvider
import com.rcll.core.middlewares.dynamic.holder.DynamicMiddlewaresHolder
import com.rcll.droidredux.redux.AppStore
import com.rcll.droidredux.ui.theme.DroidReduxTheme
import com.rcll.mainscreen.MainScreenContent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {
    private val dynamicMiddlewaresHolder: DynamicMiddlewaresHolder by inject()
    private val store: AppStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(
            ComposeView(this).apply {
                setViewCompositionStrategy(
                    strategy = object : ViewCompositionStrategy {
                        override fun installFor(view: AbstractComposeView): () -> Unit {
                            return {}
                        }
                    }
                )
                setContent {
                    DroidReduxTheme {
                        val state = store.stateFlow.collectAsState()

                        StoreStateProvider(state) {
                            StoreDispatcherProvider(store) {
                                DynamicMiddlewareManagerProvider(dynamicMiddlewaresHolder) {
                                    MainScreenContent(state.value.ui)
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}
