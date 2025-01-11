package com.rcll.timerservice.active.actions

import com.rcll.core.api.IAction

/**
 * Ивент отправлемый из таймера
 */
data class TimerEvent(val key: Any) : IAction
