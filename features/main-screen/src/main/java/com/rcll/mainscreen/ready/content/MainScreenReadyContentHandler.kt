package com.rcll.mainscreen.ready.content

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.LifecycleStartEffect
import com.rcll.core.base.LocalStore
import com.rcll.mainscreen.MainScreen
import com.rcll.timerservice.active.actions.StopTimer
import com.rcll.timerservice.inactive.actions.ActivateTimer

@Composable
internal fun MainScreenReadyContentHandler(readyState: MainScreen.Ready) {
    CounterTimerHandler(readyState.timer.key)
}

@Composable
internal fun CounterTimerHandler(timerKey: Any) {
    val store = LocalStore.current

    LifecycleStartEffect(timerKey) {
        store.dispatch(ActivateTimer(timerKey) )

        onStopOrDispose {
            store.dispatch(StopTimer(timerKey))
        }
    }
}
