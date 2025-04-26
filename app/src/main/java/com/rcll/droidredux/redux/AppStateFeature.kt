package com.rcll.droidredux.redux

import androidx.compose.runtime.Composable
import com.rcll.domain.provider.UsersProviderFeature
import com.rcll.droidredux.redux.entities.Entities
import com.rcll.mainscreen.MainScreenFeature
import com.rcll.timerservice.TimerFeature

@Composable
fun AppStateFeature(state: AppState) {
    EntitiesFeature(state.entities)
    MainScreenFeature(state.ui)
}

@Composable
fun EntitiesFeature(entities: Entities) {
    UsersProviderFeature(entities.providers.usersProvider)
    TimerFeature(entities.timer)
}
