package com.rcll.timerservice.active.actions

import com.rcll.core.api.IAction
import com.rcll.timerservice.TimerKey

/**
 * Ивент отправлемый из таймера
 */
data class TimerEvent(val key: TimerKey) : IAction
