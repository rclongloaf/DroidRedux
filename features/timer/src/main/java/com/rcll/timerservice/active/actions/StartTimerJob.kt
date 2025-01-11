package com.rcll.timerservice.active.actions

import com.rcll.core.api.IStore
import com.rcll.core.base.LaunchedAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

/**
 * Запускается при переходе таймера в активное состояние.
 * Останавливается при переходе таймера в неактивное состояние.
 */
internal data class StartTimerJob(
    val key: Any,
    override val scope: CoroutineScope
) : LaunchedAction() {

    override suspend fun launch(store: IStore<*>) {
        val event = TimerEvent(key)

        while (true) {
            delay(1000)

            store.dispatch(event)
        }
    }
}

