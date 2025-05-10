package com.rcll.droidredux.ui

import androidx.compose.runtime.State
import com.rcll.mainscreen.ui.MainScreenUI

data class AppUI(
    val mainScreenUI: State<MainScreenUI>
)