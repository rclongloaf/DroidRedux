package com.rcll.timerservice.active.handlers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rcll.core.base.LocalStoreDispatcher
import com.rcll.timerservice.TimerKey
import com.rcll.timerservice.active.actions.TimerEvent
import kotlinx.coroutines.delay

@Composable
internal fun TimerActiveHandler(key: TimerKey) {
    val store = LocalStoreDispatcher.current

    LaunchedEffect(key) {
        val event = TimerEvent(key)

        while (true) {
            delay(1000)

            store.dispatch(event)
        }
    }
}
