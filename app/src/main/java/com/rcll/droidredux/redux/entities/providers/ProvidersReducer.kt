package com.rcll.droidredux.redux.entities.providers

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.domain.provider.reducer.UsersProviderReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface ProvidersReducer : Reducer<MutableProviders>

class ProvidersReducerImpl : ProvidersReducer, KoinComponent {
    private val usersProviderReducer: UsersProviderReducer by inject()

    override fun reduce(state: MutableProviders, action: Action): MutableProviders {
        return state.smartCopy(
            usersProvider = usersProviderReducer.reduce(state.usersProvider, action)
        )
    }
}