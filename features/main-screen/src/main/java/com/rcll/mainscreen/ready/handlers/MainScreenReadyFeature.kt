package com.rcll.mainscreen.ready.handlers

import androidx.compose.runtime.Composable
import com.rcll.core.api.IReducer
import com.rcll.core.middlewares.dynamic.DynamicMiddleware
import com.rcll.core.middlewares.dynamic.WithDynamicMiddleware
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.ready.actions.CounterAction
import com.rcll.navigation.NavigationFeature
import com.rcll.timerservice.TimerFeature
import com.rcll.timerservice.TimerKey
import com.rcll.timerservice.active.actions.TimerEvent
import kotlin.reflect.KClass

@Composable
internal fun MainScreenReadyFeature(ready: MainScreen.Ready) {
    val timer = ready.timer
    val timerKey = timer.key

    TimerFeature(timer)
    TimerEventMediator(timerKey)

    NavigationFeature(ready.navigation) { tab ->
        //handle every tabs state

    }
}

@Composable
internal fun TimerEventMediator(timerKey: TimerKey) {
    WithDynamicMiddleware(timerKey, TimerEventHandler(timerKey))
}

internal data class TimerEventHandler(
    val timerKey: Any,
) : DynamicMiddleware<TimerEvent>() {
    override val actionClassFilter: KClass<TimerEvent> = TimerEvent::class

    override fun <TState> reduceBeforeNextReducer(
        state: TState,
        action: TimerEvent,
        newActionReducer: IReducer<TState>
    ): TState {
        if (action.key != timerKey) return state

        return newActionReducer.reduce(state, CounterAction.Increase)
    }
}
