package com.rcll.timerservice.inactive.actions

import com.rcll.core.api.IPatch

/**
 * Интерфейс для патчей, которые применяются в неактивном состоянии таймера
 */
internal sealed interface InactiveStatePatch: IPatch

/**
 * Перевеводит таймер в активное состояние
 */
data class ActivateTimer(val key: Any): InactiveStatePatch
