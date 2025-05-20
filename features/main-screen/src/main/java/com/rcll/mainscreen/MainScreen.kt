package com.rcll.mainscreen

import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.MutableNavigation
import com.rcll.navigation.Navigation
import com.rcll.timerservice.MutableTimer
import com.rcll.timerservice.Timer

sealed interface MainScreen {
    sealed interface Init : MainScreen
    sealed interface Ready : MainScreen {
        val counter: Int
        val timer: Timer
        val navigation: Navigation<MainTabKey, String>
    }
}

sealed interface MutableMainScreen : MainScreen {
    data object Init : MutableMainScreen, MainScreen.Init
    data class Ready(
        override val counter: Int,
        override val timer: MutableTimer,
        override val navigation: MutableNavigation<MainTabKey, String>
    ) : MutableMainScreen, MainScreen.Ready {

        fun smartCopy(
            newCounter: Int,
            newTimer: MutableTimer,
            newNavigation: MutableNavigation<MainTabKey, String>
        ): Ready {
            if (newCounter != counter ||
                newTimer !== timer ||
                newNavigation !== navigation
            ) {
                return Ready(
                    counter = newCounter,
                    timer = newTimer,
                    navigation = newNavigation
                )
            }

            return this
        }
    }
}

internal data object MainScreenTimerKey
