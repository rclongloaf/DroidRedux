package com.rcll.droidredux.redux

import androidx.compose.runtime.Stable
import com.rcll.droidredux.redux.domain.DomainData
import com.rcll.droidredux.redux.entities.Entities
import com.rcll.mainscreen.MainScreen

@Stable
data class AppState(
    val domain: DomainData = DomainData(),
    val entities: Entities = Entities(),
    val ui: MainScreen = MainScreen.Init
) {
    fun smartCopy(
        domain: DomainData,
        entities: Entities,
        ui: MainScreen,
    ): AppState {
        if (domain !== this.domain ||
            entities !== this.entities ||
            ui !== this.ui
        ) {
            return AppState(
                domain = domain,
                entities = entities,
                ui = ui
            )
        }
        return this
    }
}