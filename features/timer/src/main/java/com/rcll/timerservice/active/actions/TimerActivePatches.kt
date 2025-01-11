package com.rcll.timerservice.active.actions

import com.rcll.core.api.IPatch

/**
 * Интерфейс для патчей, которые применяются в активном состоянии таймера
 */
internal sealed interface ActiveStatePatch: IPatch

/**
 * Переводит таймер в неактивное состояние
 */
data class StopTimer(val key: Any) : ActiveStatePatch
