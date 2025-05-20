package com.rcll.timerservice.inactive.actions

import com.rcll.timerservice.TimerAction
import com.rcll.timerservice.TimerKey

/**
 * Интерфейс для экшенов, которые применяются в неактивном состоянии таймера
 */
internal sealed interface InactiveStateAction : TimerAction

/**
 * Перевеводит таймер в активное состояние
 */
data class ActivateTimer(override val key: TimerKey) : InactiveStateAction
