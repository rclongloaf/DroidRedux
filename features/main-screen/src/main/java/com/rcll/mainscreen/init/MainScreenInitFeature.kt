package com.rcll.mainscreen.init

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rcll.core.base.LocalStoreDispatcher
import com.rcll.mainscreen.init.actions.OnReady
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun MainScreenInitFeature() {
    val store = LocalStoreDispatcher.current

    LaunchedEffect(Unit) {
        launch {
            delay(1000)
            store.dispatch(OnReady(0))
        }
    }
}
