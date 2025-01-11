package com.rcll.timerservice

data class Timer(
    val key: Any,
    val state: TimerState = TimerState.Inactive
)

sealed interface TimerState {
    data object Inactive : TimerState
    data object Active : TimerState
}
