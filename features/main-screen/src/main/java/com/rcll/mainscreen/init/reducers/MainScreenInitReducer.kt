package com.rcll.mainscreen.init.reducers

import com.rcll.core.api.IPatch
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.MainScreenTimerKey
import com.rcll.mainscreen.init.actions.InitPatch
import com.rcll.mainscreen.init.actions.OnReady
import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.Navigation
import com.rcll.navigation.Tab
import com.rcll.timerservice.Timer
import com.rcll.timerservice.TimerState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal fun reduceInit(state: MainScreen.Init, patch: IPatch) : MainScreen {
    if (patch !is InitPatch) return state

    return when (patch) {
        is OnReady -> {
            MainScreen.Ready(
                counter = patch.counter,
                timer = Timer(
                    key = MainScreenTimerKey,
                    state = TimerState.Active
                ),
                navigation = Navigation<MainTabKey, String>(
                    tabs = navigationTabs(),
                    activeTabKey = MainTabKey.Profile
                )
            )
        }
    }
}

private fun navigationTabs() : ImmutableList<Tab<MainTabKey, String>> {
    return persistentListOf(
        Tab(
            key = MainTabKey.Profile,
            data = "Profile content"
        ),
        Tab(
            key = MainTabKey.Feed,
            data = "Feed content"
        ),
        Tab(
            key = MainTabKey.Settings,
            data = "Settings content"
        )
    )
}
