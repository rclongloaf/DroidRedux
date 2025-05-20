package com.rcll.timerservice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

@Stable
interface Timer {
    val key: TimerKey
    val timerState: State<TimerState>
}

@JvmInline
@Stable
value class TimerKey(val value: Any)

fun Any.asTimerKey() = TimerKey(this)

sealed interface TimerState {
    data object Inactive : TimerState
    data object Active : TimerState
}

data class MutableTimer(
    override val key: TimerKey,
    override val timerState: MutableState<TimerState> = mutableStateOf(TimerState.Inactive)
) : Timer
