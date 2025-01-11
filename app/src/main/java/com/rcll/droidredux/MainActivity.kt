package com.rcll.droidredux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.rcll.core.base.StoreProvider
import com.rcll.droidredux.ui.theme.DroidReduxTheme
import com.rcll.mainscreen.MainScreenContent

class MainActivity : ComponentActivity() {
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
                        StoreProvider(store) {
                            val state = store.stateFlow.collectAsState()
                            MainScreenContent(state.value.ui)
                        }
                    }
                }
            }
        )
    }
}
