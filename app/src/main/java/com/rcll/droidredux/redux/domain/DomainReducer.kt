package com.rcll.droidredux.redux.domain

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.domain.cache.UsersCacheReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface DomainReducer : Reducer<MutableDomainData>

class DomainReducerImpl : DomainReducer, KoinComponent {
    private val usersCacheReducer: UsersCacheReducer by inject()

    override fun reduce(state: MutableDomainData, action: Action): MutableDomainData {
        return state.smartCopy(
            usersCache = usersCacheReducer.reduce(state.usersCache, action)
        )
    }
}