package com.rcll.mainscreen.ready.actions

import com.rcll.core.api.IAction

internal sealed interface CounterAction : IAction {
    data object Increase : CounterAction
    data object Decrease : CounterAction
}
