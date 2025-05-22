package com.rcll.core.middlewares.dynamic

import com.rcll.core.api.Action

class DeferredDynamicActionsHolder {
    private val deferredActions = mutableListOf<Pair<Action, DynamicActionObserver<out Action>>>()

    fun add(action: Action, dynamicActionObserver: DynamicActionObserver<out Action>) {
        deferredActions.add(action to dynamicActionObserver)
    }

    fun processDeferredActions() {
        for ((action, dynamicActionObserver) in deferredActions) {
            dynamicActionObserver.observeActionUntyped(action)
        }
    }

    fun clearDeferredActions() {
        deferredActions.clear()
    }
}
