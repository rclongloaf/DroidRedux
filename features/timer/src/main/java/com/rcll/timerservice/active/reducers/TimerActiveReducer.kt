package com.rcll.timerservice.active.reducers

import com.rcll.core.api.IPatch
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.active.actions.ActiveStatePatch
import com.rcll.timerservice.active.actions.StopTimer


internal fun reduceActive(state: TimerState.Active, key: Any, patch: IPatch): TimerState {
    if (patch !is ActiveStatePatch) return state

    return when (patch) {
        is StopTimer -> {
            if (patch.key != key) return state

            TimerState.Inactive
        }
    }
}

