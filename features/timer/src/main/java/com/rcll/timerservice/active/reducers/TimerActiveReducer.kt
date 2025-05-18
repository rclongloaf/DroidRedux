package com.rcll.timerservice.active.reducers

import com.rcll.core.api.Action
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.active.actions.ActiveStateAction
import com.rcll.timerservice.active.actions.StopTimer


internal fun reduceActive(state: TimerState.Active, key: Any, action: Action): TimerState {
    if (action !is ActiveStateAction) return state

    return when (action) {
        is StopTimer -> {
            if (action.key != key) return state

            TimerState.Inactive
        }
    }
}

