package com.rcll.mainscreen;

import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.Navigation
import com.rcll.timerservice.Timer

sealed interface MainScreen {
    data object Init : MainScreen
    data class Ready(
        val counter: Int,
        val timer: Timer,
        val navigation: Navigation<MainTabKey, String>
    ) : MainScreen
}

internal data object MainScreenTimerKey
