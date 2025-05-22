package com.rcll.timerservice

import androidx.compose.runtime.Composable
import com.rcll.timerservice.active.handlers.TimerActiveFeature

@Composable
fun TimerFeature(timer: Timer) {
    val state = timer.timerState
    when (state) {
        is TimerState.Active -> TimerActiveFeature(timer.key)
        TimerState.Inactive -> Unit
    }
}
