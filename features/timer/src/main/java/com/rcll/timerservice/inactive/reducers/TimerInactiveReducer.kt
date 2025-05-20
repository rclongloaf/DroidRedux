package com.rcll.timerservice.inactive.reducers

import com.rcll.timerservice.TimerAction
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.inactive.actions.ActivateTimer
import com.rcll.timerservice.inactive.actions.InactiveStateAction

internal fun reduceInactive(state: TimerState.Inactive, action: TimerAction): TimerState {
    if (action !is InactiveStateAction) return state

    return when (action) {
        is ActivateTimer -> {
            TimerState.Active
        }
    }
}
