package com.rcll.timerservice

import com.rcll.core.api.Action

interface TimerAction : Action {
    val key: TimerKey
}