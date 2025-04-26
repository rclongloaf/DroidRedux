package com.rcll.timerservice

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import com.rcll.timerservice.active.reducers.reduceActive
import com.rcll.timerservice.inactive.reducers.reduceInactive

interface TimerReducer : IReducer<Timer>

class TimerReducerImpl : TimerReducer {
    override fun reduce(state: Timer, action: IAction): Timer {
        val newState = when (val timerState = state.timerState) {
            is TimerState.Active -> reduceActive(timerState, state.key, action)
            is TimerState.Inactive -> reduceInactive(timerState, state.key, action)
        }

        return state.smartCopy(
            key = state.key,
            state = newState
        )
    }

}
