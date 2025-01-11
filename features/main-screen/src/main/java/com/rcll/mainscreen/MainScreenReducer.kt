package com.rcll.mainscreen

import com.rcll.core.api.IPatch
import com.rcll.mainscreen.init.reducers.reduceInit
import com.rcll.mainscreen.ready.reducers.reduceReady

fun reduceMainScreen(mainScreen: MainScreen, patch: IPatch) : MainScreen {
    return when (mainScreen) {
        is MainScreen.Init -> reduceInit(mainScreen, patch)
        is MainScreen.Ready -> reduceReady(mainScreen, patch)
    }
}
