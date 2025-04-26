package com.rcll.mainscreen.init.reducers

import com.rcll.core.api.IAction
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.MainScreenTimerKey
import com.rcll.mainscreen.init.actions.InitAction
import com.rcll.mainscreen.init.actions.OnReady
import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.Navigation
import com.rcll.navigation.Tab
import com.rcll.navigation.asNavigationKey
import com.rcll.timerservice.Timer
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.asTimerKey
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal fun reduceInit(state: MainScreen.Init, action: IAction): MainScreen {
    if (action !is InitAction) return state

    return when (action) {
        is OnReady -> {
            MainScreen.Ready(
                counter = action.counter,
                timer = Timer(
                    key = MainScreenTimerKey.asTimerKey(),
                    timerState = TimerState.Active
                ),
                navigation = Navigation<MainTabKey, String>(
                    key = MainScreenNavigation.asNavigationKey(),
                    tabs = navigationTabs(),
                    activeTabKey = MainTabKey.Profile
                )
            )
        }
    }
}

object MainScreenNavigation

private fun navigationTabs(): PersistentList<Tab<MainTabKey, String>> {
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
