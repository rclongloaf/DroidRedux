package com.rcll.droidredux.redux

import com.rcll.mainscreen.MainScreen
import com.rcll.timerservice.Timer
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

data class AppState(
    val domain: DomainData = DomainData(),
    val entities: Entities = Entities(),
    val ui: MainScreen = MainScreen.Init
)

data class DomainData(
    val users: ImmutableMap<Int, String> = persistentMapOf<Int, String>(),
)

data class Entities(
    val timer: Timer = Timer(TIMER_SERVICE_KEY)
)

const val TIMER_SERVICE_KEY = 1001L
