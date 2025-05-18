package com.rcll.mainscreen.ready.reducers

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.ready.actions.CounterAction
import com.rcll.timerservice.TimerReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MainScreenReadyReducer : Reducer<MainScreen.Ready>

class MainScreenReadyReducerImpl : MainScreenReadyReducer, KoinComponent {
    private val timerReducer: TimerReducer by inject()
    private val navigationReducer: MainNavigationReducer by inject()

    override fun reduce(state: MainScreen.Ready, action: Action): MainScreen.Ready {
        val newTimer = timerReducer.reduce(state.timer, action)
        val newCounter = reduceCounter(state.counter, action)
        val newNavigation = navigationReducer.reduce(state.navigation, action)

        return state.smartCopy(
            newCounter = newCounter,
            newTimer = newTimer,
            newNavigation = newNavigation
        )
    }

    private fun reduceCounter(counter: Int, action: Action): Int {
        if (action !is CounterAction) return counter

        return when (action) {
            is CounterAction.Increase -> increaseCounter(counter)
            is CounterAction.Decrease -> decreaseCounter(counter)
        }
    }

    private fun increaseCounter(counter: Int): Int {
        return counter + 1
    }

    private fun decreaseCounter(counter: Int): Int {
        return counter - 1
    }
}