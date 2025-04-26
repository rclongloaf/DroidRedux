package com.rcll.droidredux.redux

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import com.rcll.droidredux.redux.domain.DomainReducer
import com.rcll.droidredux.redux.entities.EntitiesReducer
import com.rcll.mainscreen.MainScreenReducer

class AppStateReducer(
    private val domainReducer: DomainReducer,
    private val entitiesReducer: EntitiesReducer,
    private val mainScreenReducer: MainScreenReducer
) : IReducer<AppState> {
    override fun reduce(state: AppState, action: IAction): AppState {
        return state.smartCopy(
            domain = domainReducer.reduce(state.domain, action),
            entities = entitiesReducer.reduce(state.entities, action),
            ui = mainScreenReducer.reduce(state.ui, action),
        )
    }
}
