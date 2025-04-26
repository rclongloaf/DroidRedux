package com.rcll.timerservice.active.actions

import com.rcll.core.api.IAction

/**
 * Интерфейс для экшенов, которые применяются в активном состоянии таймера
 */
internal sealed interface ActiveStateAction : IAction

/**
 * Переводит таймер в неактивное состояние
 */
data class StopTimer(val key: Any) : ActiveStateAction
