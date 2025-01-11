package com.rcll.droidredux.redux

import androidx.compose.runtime.Composable
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.MainScreenHandler
import com.rcll.timerservice.TimerHandler

@Composable
fun AppStateHandler(state: AppState) {
    EntitiesHandler(state.entities)
    MainScreenHandler(state.ui)
}

@Composable
fun EntitiesHandler(entities: Entities) {
    TimerHandler(entities.timer)
}
