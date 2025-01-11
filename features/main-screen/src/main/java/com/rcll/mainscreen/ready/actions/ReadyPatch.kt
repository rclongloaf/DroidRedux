package com.rcll.mainscreen.ready.actions

import com.rcll.core.api.IPatch

internal sealed interface CounterPatch: IPatch {
    data object Increase : CounterPatch
    data object Decrease : CounterPatch
}
