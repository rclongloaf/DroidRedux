package com.rcll.timerservice

import com.rcll.core.api.IPatch
import com.rcll.timerservice.active.reducers.reduceActive
import com.rcll.timerservice.inactive.reducers.reduceInactive

fun reduceTimer(timer: Timer, patch: IPatch): Timer {
    val state = timer.state
    val newState = when (state) {
        is TimerState.Active -> reduceActive(state, timer.key, patch)
        is TimerState.Inactive -> reduceInactive(state, timer.key, patch)
    }

    if (timer.state == newState) return timer

    return Timer(
        key = timer.key,
        state = newState
    )
}
