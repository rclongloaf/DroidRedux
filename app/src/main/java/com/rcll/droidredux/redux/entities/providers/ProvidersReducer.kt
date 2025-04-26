package com.rcll.droidredux.redux.entities.providers

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import com.rcll.domain.provider.UsersProviderReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface ProvidersReducer : IReducer<Providers>

class ProvidersReducerImpl : ProvidersReducer, KoinComponent {
    private val usersProviderReducer: UsersProviderReducer by inject()

    override fun reduce(state: Providers, action: IAction): Providers {
        return state.smartCopy(
            usersProvider = usersProviderReducer.reduce(state.usersProvider, action)
        )
    }
}