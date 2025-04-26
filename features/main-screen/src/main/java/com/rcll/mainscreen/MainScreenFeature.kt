package com.rcll.mainscreen

import androidx.compose.runtime.Composable
import com.rcll.mainscreen.init.MainScreenInitFeature
import com.rcll.mainscreen.ready.handlers.MainScreenReadyFeature

@Composable
fun MainScreenFeature(mainScreen: MainScreen) {
    when (mainScreen) {
        is MainScreen.Init -> MainScreenInitFeature()
        is MainScreen.Ready -> MainScreenReadyFeature(mainScreen)
    }
}
