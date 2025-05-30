package com.rcll.mainscreen.ready.feature

import androidx.compose.runtime.Composable
import com.rcll.mainscreen.MainScreen
import com.rcll.navigation.NavigationFeature
import com.rcll.timerservice.TimerFeature

@Composable
internal fun MainScreenReadyFeature(ready: MainScreen.Ready) {
    val timer = ready.timer

    TimerFeature(timer)

    NavigationFeature(ready.navigation) { tab ->
        //handle every tabs state

    }
}
