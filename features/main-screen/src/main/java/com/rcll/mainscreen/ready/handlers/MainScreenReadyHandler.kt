package com.rcll.mainscreen.ready.handlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonSkippableComposable
import com.rcll.core.api.IAction
import com.rcll.core.api.IDispatcher
import com.rcll.core.api.IStore
import com.rcll.core.base.LocalStore
import com.rcll.core.middlewares.WithDynamicDispatcher
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.ready.actions.CounterPatch
import com.rcll.navigation.NavigationHandler
import com.rcll.timerservice.TimerHandler
import com.rcll.timerservice.active.actions.TimerEvent

@Composable
@NonSkippableComposable
internal fun MainScreenReadyHandler(ready: MainScreen.Ready) {
    val timer = ready.timer
    val timerKey = timer.key

    TimerHandler(timer)
    TimerEventMediator(timerKey)

    NavigationHandler(ready.navigation) { tab ->
        //handle every tabs state
        LaunchedEffect(tab.key) {

        }
    }
}

@Composable
internal fun TimerEventMediator(timerKey: Any) {
    val store = LocalStore.current
    WithDynamicDispatcher(timerKey, TimerEventHandler(timerKey, store))
}

internal data class TimerEventHandler(
    val timerKey: Any,
    val store: IStore<*>
) : IDispatcher {
    override fun dispatch(action: IAction) {
        if (action !is TimerEvent || action.key != timerKey) return

        store.dispatch(CounterPatch.Increase)
    }
}
