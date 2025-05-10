package com.rcll.mainscreen.ui

import androidx.compose.runtime.State
import com.rcll.navigation.ui.NavigationUI

sealed interface MainScreenUI {
    data object Init : MainScreenUI
    data class Ready(
        val counter: Int,
        val navigationUI: State<NavigationUI<MainTabUI>>
    ) : MainScreenUI
}

data class MainTabUI(
    val title: String
)