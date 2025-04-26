package com.rcll.droidredux.redux.entities.providers

import com.rcll.droidredux.redux.entities.EntitiesSelector

class ProvidersSelector(
    private val entitiesSelector: EntitiesSelector
) {
    fun get(state: Any): Providers {
        return entitiesSelector.get(state).providers
    }
}