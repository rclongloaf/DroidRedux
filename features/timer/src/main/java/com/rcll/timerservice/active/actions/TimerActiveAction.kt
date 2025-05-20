package com.rcll.timerservice.active.actions

import com.rcll.timerservice.TimerAction
import com.rcll.timerservice.TimerKey

/**
 * Интерфейс для экшенов, которые применяются в активном состоянии таймера
 */
internal sealed interface ActiveStateAction : TimerAction

/**
 * Переводит таймер в неактивное состояние
 */
data class StopTimer(override val key: TimerKey) : ActiveStateAction
