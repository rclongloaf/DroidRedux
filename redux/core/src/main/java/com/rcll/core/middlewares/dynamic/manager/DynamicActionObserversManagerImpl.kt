package com.rcll.core.middlewares.dynamic.manager

import com.rcll.core.api.Action
import com.rcll.core.middlewares.dynamic.DynamicActionObserver
import com.rcll.core.middlewares.dynamic.provider.DynamicActionObserversProvider

class DynamicActionObserversManagerImpl(
    private val dynamicActionObserversProvider: DynamicActionObserversProvider
) : DynamicActionObserversManager {
    override fun subscribe(dynamicActionObserver: DynamicActionObserver<out Action>) {
        dynamicActionObserversProvider.add(dynamicActionObserver)
    }

    override fun unsubscribe(dynamicActionObserver: DynamicActionObserver<out Action>) {
        dynamicActionObserversProvider.remove(dynamicActionObserver)
    }
}