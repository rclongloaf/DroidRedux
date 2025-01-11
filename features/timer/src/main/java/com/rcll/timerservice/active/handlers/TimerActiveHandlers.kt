package com.rcll.timerservice.active.handlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rcll.core.base.LocalStore
import com.rcll.timerservice.active.actions.StartTimerJob


@Composable
internal fun TimerActiveHandler(key: Any) {
    val store = LocalStore.current

    LaunchedEffect(key) {
        store.dispatch(StartTimerJob(key, this))
    }
}
