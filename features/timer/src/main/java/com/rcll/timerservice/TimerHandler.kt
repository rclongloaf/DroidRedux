package com.rcll.timerservice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import com.rcll.timerservice.active.handlers.TimerActiveHandler


@Composable
@NonSkippableComposable
fun TimerHandler(timer: Timer) {
    val state = timer.state
    when (state) {
        is TimerState.Active -> TimerActiveHandler(timer.key)
        TimerState.Inactive -> Unit
    }
}
