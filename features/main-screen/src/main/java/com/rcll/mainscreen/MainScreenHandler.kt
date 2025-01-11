package com.rcll.mainscreen

import androidx.compose.runtime.Composable
import com.rcll.mainscreen.init.MainScreenInitHandler
import com.rcll.mainscreen.ready.handlers.MainScreenReadyHandler

@Composable
fun MainScreenHandler(mainScreen: MainScreen) {
    when (mainScreen) {
        is MainScreen.Init -> MainScreenInitHandler()
        is MainScreen.Ready -> MainScreenReadyHandler(mainScreen)
    }
}
