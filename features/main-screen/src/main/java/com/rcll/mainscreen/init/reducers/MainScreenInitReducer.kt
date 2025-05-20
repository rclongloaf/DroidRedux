package com.rcll.mainscreen.init.reducers

import androidx.compose.runtime.mutableStateOf
import com.rcll.core.api.Action
import com.rcll.mainscreen.MainScreenTimerKey
import com.rcll.mainscreen.MutableMainScreen
import com.rcll.mainscreen.init.actions.InitAction
import com.rcll.mainscreen.init.actions.OnReady
import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.MutableNavigation
import com.rcll.navigation.Tab
import com.rcll.navigation.asNavigationKey
import com.rcll.timerservice.MutableTimer
import com.rcll.timerservice.TimerState
import com.rcll.timerservice.asTimerKey
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal fun reduceInit(state: MutableMainScreen.Init, action: Action): MutableMainScreen {
    if (action !is InitAction) return state

    return when (action) {
        is OnReady -> {
            MutableMainScreen.Ready(
                counter = action.counter,
                timer = MutableTimer(
                    key = MainScreenTimerKey.asTimerKey(),
                    timerState = mutableStateOf(TimerState.Active)
                ),
                navigation = MutableNavigation<MainTabKey, String>(
                    key = MainScreenNavigation.asNavigationKey(),
                    tabs = mutableStateOf(navigationTabs()),
                    activeTabKey = mutableStateOf(MainTabKey.Profile)
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
