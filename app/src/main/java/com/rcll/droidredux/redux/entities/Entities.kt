package com.rcll.droidredux.redux.entities

import com.rcll.droidredux.redux.entities.providers.Providers
import com.rcll.timerservice.Timer
import com.rcll.timerservice.asTimerKey

data class Entities(
    val providers: Providers = Providers(),
    val timer: Timer = Timer(TIMER_SERVICE_KEY.asTimerKey())
) {
    fun smartCopy(
        providers: Providers,
        timer: Timer,
    ): Entities {
        if (providers !== this.providers ||
            timer !== this.timer
        ) {
            return Entities(
                providers = providers,
                timer = timer
            )
        }
        return this
    }
}

const val TIMER_SERVICE_KEY = 1001L
