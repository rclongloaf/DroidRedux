package com.rcll.droidredux.redux.reducer

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.droidredux.redux.AppState
import com.rcll.droidredux.redux.domain.DomainReducer
import com.rcll.droidredux.redux.entities.EntitiesReducer
import com.rcll.mainscreen.common.reducer.MainScreenReducer

class AppStateReducer(
    private val domainReducer: DomainReducer,
    private val entitiesReducer: EntitiesReducer,
    private val mainScreenReducer: MainScreenReducer
) : Reducer<AppState> {
    override fun reduce(state: AppState, action: Action): AppState {
        return state.smartCopy(
            domain = domainReducer.reduce(state.domain, action),
            entities = entitiesReducer.reduce(state.entities, action),
            ui = mainScreenReducer.reduce(state.ui, action),
        )
    }
}
