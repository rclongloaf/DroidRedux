package com.rcll.droidredux.redux.entities

import com.rcll.droidredux.redux.AppState

class EntitiesSelector {
    fun get(state: Any): Entities {
        return (state as AppState).entities
    }
}