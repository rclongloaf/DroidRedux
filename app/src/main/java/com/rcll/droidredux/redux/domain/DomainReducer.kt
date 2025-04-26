package com.rcll.droidredux.redux.domain

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import com.rcll.domain.cache.UsersCacheReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface DomainReducer : IReducer<DomainData>

class DomainReducerImpl : DomainReducer, KoinComponent {
    private val usersCacheReducer: UsersCacheReducer by inject()

    override fun reduce(state: DomainData, action: IAction): DomainData {
        return state.smartCopy(
            usersCache = usersCacheReducer.reduce(state.usersCache, action)
        )
    }
}