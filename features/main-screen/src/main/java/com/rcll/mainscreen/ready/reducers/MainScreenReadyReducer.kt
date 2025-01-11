package com.rcll.mainscreen.ready.reducers

import com.rcll.core.api.IPatch
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.ready.actions.CounterPatch
import com.rcll.navigation.reduceNavigation
import com.rcll.timerservice.reduceTimer

internal fun reduceReady(state: MainScreen.Ready, patch: IPatch) : MainScreen {
    val newTimer = reduceTimer(state.timer, patch)
    val newCounter = reduceCounter(state.counter, patch)
    val navigation = reduceNavigation(state.navigation, patch) { data, patch ->
        return@reduceNavigation data
    }

    if (
        state.counter == newCounter &&
        state.timer == newTimer &&
        state.navigation == navigation
    ) return state

    return MainScreen.Ready(
        counter = newCounter,
        timer = newTimer,
        navigation = navigation
    )
}

internal fun reduceCounter(counter: Int, patch: IPatch) : Int{
    if (patch !is CounterPatch) return counter

    return when (patch) {
        is CounterPatch.Increase -> increaseCounter(counter)
        is CounterPatch.Decrease -> decreaseCounter(counter)
    }
}

internal fun increaseCounter(counter: Int) : Int {
    return counter + 1
}

internal fun decreaseCounter(counter: Int) : Int {
    return counter - 1
}
