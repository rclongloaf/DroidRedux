package com.rcll.core.base

import com.rcll.core.api.IRunnableAction
import com.rcll.core.api.IStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class LaunchedAction : IRunnableAction {
    abstract val scope: CoroutineScope

    override fun run(store: IStore<*>) {
        scope.launch {
            launch(store)
        }
    }

    abstract suspend fun launch(store: IStore<*>)
}
