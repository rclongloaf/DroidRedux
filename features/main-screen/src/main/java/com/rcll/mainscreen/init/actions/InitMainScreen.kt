package com.rcll.mainscreen.init.actions

import com.rcll.core.api.IStore
import com.rcll.core.base.LaunchedAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

internal data class InitMainScreen(override val scope: CoroutineScope) : LaunchedAction() {
    override suspend fun launch(store: IStore<*>) {
        delay(1000)
        store.dispatch(OnReady(0))
    }
}
