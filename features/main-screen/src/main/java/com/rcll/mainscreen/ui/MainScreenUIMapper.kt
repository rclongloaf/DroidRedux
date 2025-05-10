package com.rcll.mainscreen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.rcll.compose.rememberLateinitMutableState
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.Tab
import com.rcll.navigation.ui.NavigationUI
import com.rcll.navigation.ui.NavigationUIMapper

@Composable
fun MainScreenUIMapper(
    mainScreenUIState: MutableState<MainScreenUI>,
    mainScreen: MainScreen
) {
    mainScreenUIState.value = when (mainScreen) {
        MainScreen.Init -> MainScreenUI.Init
        is MainScreen.Ready -> {
            val navigationUIState = rememberLateinitMutableState<NavigationUI<MainTabUI>>()
            NavigationUIMapper(
                navigationUIState = navigationUIState,
                navigation = mainScreen.navigation,
            ) { tabUIState, tab ->
                MainTabUIMapper(tabUIState, tab)
            }

            MainScreenUI.Ready(
                counter = mainScreen.counter,
                navigationUI = navigationUIState
            )
        }
    }
}

@Composable
fun MainTabUIMapper(
    tabUIState: MutableState<MainTabUI>,
    tab: Tab<MainTabKey, String>
) {
    tabUIState.value = MainTabUI(
        title = tab.data
    )
}