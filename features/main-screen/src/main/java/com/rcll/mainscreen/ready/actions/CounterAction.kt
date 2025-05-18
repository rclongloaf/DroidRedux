package com.rcll.mainscreen.ready.actions

import com.rcll.core.api.Action

internal sealed interface CounterAction : Action {
    data object Increase : CounterAction
    data object Decrease : CounterAction
}
