package com.rcll.timerservice

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.timerservice.active.reducers.reduceActive
import com.rcll.timerservice.inactive.reducers.reduceInactive

interface TimerReducer : Reducer<Timer>

class TimerReducerImpl : TimerReducer {
    override fun reduce(state: Timer, action: Action): Timer {
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
