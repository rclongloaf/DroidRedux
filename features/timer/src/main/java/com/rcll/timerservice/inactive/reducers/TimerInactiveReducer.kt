package com.rcll.timerservice.inactive.reducers

import com.rcll.core.api.IAction
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.inactive.actions.ActivateTimer
import com.rcll.timerservice.inactive.actions.InactiveStateAction


internal fun reduceInactive(state: TimerState.Inactive, key: Any, action: IAction): TimerState {
    if (action !is InactiveStateAction) return state

    return when (action) {
        is ActivateTimer -> {
            if (action.key != key) return state

            TimerState.Active
        }
    }
}
