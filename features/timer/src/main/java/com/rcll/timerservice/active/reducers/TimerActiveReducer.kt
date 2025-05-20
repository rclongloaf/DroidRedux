package com.rcll.timerservice.active.reducers

import com.rcll.timerservice.TimerAction
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.active.actions.ActiveStateAction
import com.rcll.timerservice.active.actions.StopTimer


internal fun reduceActive(state: TimerState.Active, action: TimerAction): TimerState {
    if (action !is ActiveStateAction) return state

    return when (action) {
        is StopTimer -> {
            TimerState.Inactive
        }
    }
}

