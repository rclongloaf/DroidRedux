package com.rcll.droidredux.redux.entities

import androidx.compose.runtime.Stable
import com.rcll.droidredux.redux.entities.providers.MutableProviders
import com.rcll.droidredux.redux.entities.providers.Providers
import com.rcll.timerservice.MutableTimer
import com.rcll.timerservice.Timer
import com.rcll.timerservice.asTimerKey

@Stable
interface Entities {
    val providers: Providers
    val timer: Timer
}

data class MutableEntities(
    override val providers: MutableProviders = MutableProviders(),
    override val timer: MutableTimer = MutableTimer(TIMER_SERVICE_KEY.asTimerKey())
) : Entities {

    fun smartCopy(
        providers: MutableProviders,
        timer: MutableTimer,
    ): MutableEntities {
        if (providers !== this.providers ||
            timer !== this.timer
        ) {
            return MutableEntities(
                providers = providers,
                timer = timer
            )
        }
        return this
    }
}

const val TIMER_SERVICE_KEY = 1001L
