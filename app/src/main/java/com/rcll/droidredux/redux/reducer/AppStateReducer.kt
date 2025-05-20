package com.rcll.droidredux.redux.reducer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.droidredux.redux.AppState
import com.rcll.droidredux.redux.MutableAppState
import com.rcll.droidredux.redux.domain.DomainReducer
import com.rcll.droidredux.redux.entities.EntitiesReducer
import com.rcll.mainscreen.common.reducer.MainScreenReducer

class AppStateReducer(
    private val domainReducer: DomainReducer,
    private val entitiesReducer: EntitiesReducer,
    private val mainScreenReducer: MainScreenReducer
) : Reducer<MutableAppState> {
    override fun reduce(state: MutableAppState, action: Action): MutableAppState {
        var mainScreen by state.ui

        mainScreen = mainScreenReducer.reduce(mainScreen, action)

        return state.smartCopy(
            domain = domainReducer.reduce(state.domain, action),
            entities = entitiesReducer.reduce(state.entities, action),
        )
    }
}
