package com.rcll.droidredux.redux.domain

import com.rcll.droidredux.redux.AppState

class DomainSelector {
    fun get(state: Any): DomainData {
        return (state as AppState).domain
    }
}