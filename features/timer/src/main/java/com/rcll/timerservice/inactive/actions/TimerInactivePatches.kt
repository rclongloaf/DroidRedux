package com.rcll.timerservice.inactive.actions

import com.rcll.core.api.Action

/**
 * Интерфейс для экшенов, которые применяются в неактивном состоянии таймера
 */
internal sealed interface InactiveStateAction : Action

/**
 * Перевеводит таймер в активное состояние
 */
data class ActivateTimer(val key: Any) : InactiveStateAction
