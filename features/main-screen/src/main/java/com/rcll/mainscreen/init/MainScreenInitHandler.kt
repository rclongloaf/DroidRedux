package com.rcll.mainscreen.init

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.rcll.core.base.LocalStore
import com.rcll.mainscreen.init.actions.InitMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

@Composable
internal fun MainScreenInitHandler() {
    val store = LocalStore.current

    DisposableEffect(Unit) {
        val scope = CoroutineScope(SupervisorJob() + Main)
        store.dispatch(InitMainScreen(scope))

        onDispose {
            scope.cancel()
        }
    }
}
