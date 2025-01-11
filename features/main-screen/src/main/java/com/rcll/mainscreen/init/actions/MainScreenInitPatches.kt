package com.rcll.mainscreen.init.actions

import com.rcll.core.api.IPatch

internal sealed interface InitPatch: IPatch

internal data class OnReady(val counter: Int) : InitPatch
