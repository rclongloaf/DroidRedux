package com.rcll.droidredux.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.rcll.compose.rememberLateinitMutableState
import com.rcll.droidredux.redux.AppState
import com.rcll.mainscreen.ui.MainScreenUI
import com.rcll.mainscreen.ui.MainScreenUIMapper

@Composable
fun AppUIMapper(appUIState: MutableState<AppUI>, state: AppState) {
    val mainScreenUIState = rememberLateinitMutableState<MainScreenUI>()

    MainScreenUIMapper(mainScreenUIState, state.ui)

    appUIState.value = AppUI(
        mainScreenUI = mainScreenUIState
    )
}