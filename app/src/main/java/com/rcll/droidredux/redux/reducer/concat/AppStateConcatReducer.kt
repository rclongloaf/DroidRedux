package com.rcll.droidredux.redux.reducer.concat

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.core.middlewares.concat.ConcatReducer
import com.rcll.droidredux.redux.AppState
import com.rcll.mainscreen.common.reducer.concat.MainScreenSubConcatReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppStateConcatReducer : ConcatReducer, KoinComponent {
    private val mainScreenSubConcatReducer: MainScreenSubConcatReducer by inject()

    override fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        val appState = state as AppState

        return mainScreenSubConcatReducer.reduceBeforeNextReducer(
            state = state,
            subState = appState.ui,
            action = action,
            newActionReducer = newActionReducer
        )
    }

    override fun <TState : Any> reduceAfterNextReducer(
        state: TState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        val appState = state as AppState

        return mainScreenSubConcatReducer.reduceAfterNextReducer(
            state = state,
            subState = appState.ui,
            action = action,
            newActionReducer = newActionReducer
        )
    }
}