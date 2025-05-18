package com.rcll.timerservice.active.actions

import com.rcll.core.api.Action

/**
 * Интерфейс для экшенов, которые применяются в активном состоянии таймера
 */
internal sealed interface ActiveStateAction : Action

/**
 * Переводит таймер в неактивное состояние
 */
data class StopTimer(val key: Any) : ActiveStateAction
