package com.rcll.droidredux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.rcll.core.middlewares.dynamic.DynamicMiddlewareManagerProvider
import com.rcll.core.middlewares.dynamic.manager.DynamicActionObserversManager
import com.rcll.droidredux.ui.AppUIStateProvider
import com.rcll.droidredux.ui.theme.DroidReduxTheme
import com.rcll.mainscreen.ui.MainScreenContent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {
    private val dynamicActionObserversManager: DynamicActionObserversManager by inject()
    private val uiStateProvider: AppUIStateProvider by inject()

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
                        DynamicMiddlewareManagerProvider(dynamicActionObserversManager) {
                            val appUI = uiStateProvider.uiState.value
                            MainScreenContent(appUI.mainScreenUI)
                        }
                    }
                }
            }
        )
    }
}
