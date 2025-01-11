package com.rcll.droidredux.redux

import com.rcll.core.api.IPatch
import com.rcll.mainscreen.reduceMainScreen
import com.rcll.timerservice.reduceTimer

internal fun reduceApp(state: AppState, patch: IPatch) : AppState {
    val domain = reduceDomain(state.domain, patch)
    val services = reduceEntities(state.entities, patch)
    val ui = reduceMainScreen(state.ui, patch)

    if (
        state.domain == domain &&
        state.entities == services &&
        state.ui == ui
    ) return state

    return AppState(
        domain = domain,
        entities = services,
        ui = ui,
    )
}

internal fun reduceDomain(state: DomainData, patch: IPatch): DomainData {
    return state
}

internal fun reduceEntities(state: Entities, patch: IPatch): Entities {
    val timerService = reduceTimer(state.timer, patch)

    if (state.timer == timerService) return state

    return Entities(
        timer = timerService
    )
}
