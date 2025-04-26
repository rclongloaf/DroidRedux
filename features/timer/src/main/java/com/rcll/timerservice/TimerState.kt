package com.rcll.timerservice

import androidx.compose.runtime.Stable

data class Timer(
    val key: TimerKey,
    val timerState: TimerState = TimerState.Inactive
) {
    fun smartCopy(
        key: TimerKey,
        state: TimerState
    ): Timer {
        if (this.key != key ||
            this.timerState != state
        ) {
            return Timer(
                key = key,
                timerState = state
            )
        }
        return this
    }
}

@JvmInline
@Stable
value class TimerKey(val value: Any)

fun Any.asTimerKey() = TimerKey(this)

sealed interface TimerState {
    data object Inactive : TimerState
    data object Active : TimerState
}
