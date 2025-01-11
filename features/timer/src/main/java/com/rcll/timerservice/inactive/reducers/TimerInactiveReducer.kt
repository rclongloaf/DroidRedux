package com.rcll.timerservice.inactive.reducers

import com.rcll.core.api.IPatch
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.inactive.actions.ActivateTimer
import com.rcll.timerservice.inactive.actions.InactiveStatePatch


internal fun reduceInactive(state: TimerState.Inactive, key: Any, patch: IPatch): TimerState {
    if (patch !is InactiveStatePatch) return state

    return when (patch) {
        is ActivateTimer -> {
            if (patch.key != key) return state

            TimerState.Active
        }
    }
}
