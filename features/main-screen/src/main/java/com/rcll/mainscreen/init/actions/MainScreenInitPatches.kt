package com.rcll.mainscreen.init.actions

import com.rcll.core.api.Action

internal sealed interface InitAction : Action

internal data class OnReady(val counter: Int) : InitAction
