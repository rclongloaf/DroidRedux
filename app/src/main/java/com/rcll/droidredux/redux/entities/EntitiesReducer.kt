package com.rcll.droidredux.redux.entities

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import com.rcll.droidredux.redux.entities.providers.ProvidersReducer
import com.rcll.timerservice.TimerReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface EntitiesReducer : IReducer<Entities>

class EntitiesReducerImpl : EntitiesReducer, KoinComponent {
    private val usersProviderReducer: ProvidersReducer by inject()
    private val timerReducer: TimerReducer by inject()

    override fun reduce(state: Entities, action: IAction): Entities {
        return state.smartCopy(
            providers = usersProviderReducer.reduce(state.providers, action),
            timer = timerReducer.reduce(state.timer, action)
        )
    }
}