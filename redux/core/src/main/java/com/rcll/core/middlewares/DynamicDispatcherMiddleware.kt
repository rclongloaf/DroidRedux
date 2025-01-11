package com.rcll.core.middlewares

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.rcll.core.api.IAction
import com.rcll.core.api.IDispatcher
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.base.LocalStore

class DynamicDispatcherMiddleware : BaseMiddleware () {
    val dynamicDispatchers = mutableSetOf<IDispatcher>()

    override fun dispatch(action: IAction) {
        when (action) {
            is DynamicDispatcher.Add -> dynamicDispatchers.add(action.dispatcher)
            is DynamicDispatcher.Remove -> dynamicDispatchers.remove(action.dispatcher)
            else -> {
                for (dynamicDispatcher in dynamicDispatchers) {
                    dynamicDispatcher.dispatch(action)
                }
            }
        }

        next?.dispatch(action)
    }
}

sealed interface DynamicDispatcher: IAction {
    data class Add(val dispatcher: IDispatcher) : DynamicDispatcher
    data class Remove(val dispatcher: IDispatcher) : DynamicDispatcher
}

@Composable
fun WithDynamicDispatcher(key: Any, dispatcher: IDispatcher) {
    val store = LocalStore.current

    DisposableEffect(key) {
        store.dispatch(DynamicDispatcher.Add(dispatcher))

        onDispose {
            store.dispatch(DynamicDispatcher.Remove(dispatcher))
        }
    }
}
