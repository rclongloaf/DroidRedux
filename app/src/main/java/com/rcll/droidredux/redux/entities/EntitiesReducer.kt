package com.rcll.droidredux.redux.entities

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.droidredux.redux.entities.providers.ProvidersReducer
import com.rcll.timerservice.TimerReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface EntitiesReducer : Reducer<Entities>

class EntitiesReducerImpl : EntitiesReducer, KoinComponent {
    private val usersProviderReducer: ProvidersReducer by inject()
    private val timerReducer: TimerReducer by inject()

    override fun reduce(state: Entities, action: Action): Entities {
        return state.smartCopy(
            providers = usersProviderReducer.reduce(state.providers, action),
            timer = timerReducer.reduce(state.timer, action)
        )
    }
}