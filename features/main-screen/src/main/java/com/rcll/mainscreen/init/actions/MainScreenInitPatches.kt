package com.rcll.mainscreen.init.actions

import com.rcll.core.api.IAction

internal sealed interface InitAction : IAction

internal data class OnReady(val counter: Int) : InitAction
