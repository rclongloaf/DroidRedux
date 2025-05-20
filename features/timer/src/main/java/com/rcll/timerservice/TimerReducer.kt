package com.rcll.timerservice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.timerservice.active.reducers.reduceActive
import com.rcll.timerservice.inactive.reducers.reduceInactive

interface TimerReducer : Reducer<MutableTimer>

class TimerReducerImpl : TimerReducer {
    override fun reduce(state: MutableTimer, action: Action): MutableTimer {
        if (action !is TimerAction || action.key != state.key) return state

        var timerState by state.timerState

        timerState = when (val value = timerState) {
            is TimerState.Active -> reduceActive(value, action)
            is TimerState.Inactive -> reduceInactive(value, action)
        }

        return state
    }

}
