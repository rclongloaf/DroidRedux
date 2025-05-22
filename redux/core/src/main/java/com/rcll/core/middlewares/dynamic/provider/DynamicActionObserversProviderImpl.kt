package com.rcll.core.middlewares.dynamic.provider

import com.rcll.core.api.Action
import com.rcll.core.middlewares.dynamic.DynamicActionObserver
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentSetOf
import kotlin.reflect.KClass

typealias DynamicActionObserversMap
        = PersistentMap<KClass<out Action>, PersistentSet<DynamicActionObserver<out Action>>>

class DynamicActionObserversProviderImpl : DynamicActionObserversProvider {
    private var dynamicActionObserversMutable: DynamicActionObserversMap = persistentHashMapOf()

    override val dynamicActionObserversMap: DynamicActionObserversImmutableMap
        get() = dynamicActionObserversMutable

    override fun add(dynamicActionObserver: DynamicActionObserver<out Action>) {
        synchronized(this) {
            var dispatchersSet =
                dynamicActionObserversMutable[dynamicActionObserver.actionFilterClass]
            if (dispatchersSet == null) {
                dispatchersSet = persistentSetOf(dynamicActionObserver)
            } else {
                if (dispatchersSet.contains(dynamicActionObserver)) {
                    throw IllegalStateException("Dynamic dispatcher already exists")
                }
                dispatchersSet = dispatchersSet.add(dynamicActionObserver)
            }
            dynamicActionObserversMutable =
                dynamicActionObserversMutable.put(
                    dynamicActionObserver.actionFilterClass,
                    dispatchersSet
                )
        }
    }

    override fun remove(dynamicActionObserver: DynamicActionObserver<out Action>) {
        synchronized(this) {
            var dispatchersSet =
                dynamicActionObserversMutable[dynamicActionObserver.actionFilterClass]
            if (dispatchersSet == null || !dispatchersSet.contains(dynamicActionObserver)) {
                throw IllegalStateException("Dynamic dispatcher not found")
            }
            if (dispatchersSet.size == 1) {
                dynamicActionObserversMutable =
                    dynamicActionObserversMutable.remove(dynamicActionObserver.actionFilterClass)
            } else {
                dispatchersSet = dispatchersSet.remove(dynamicActionObserver)
                dynamicActionObserversMutable = dynamicActionObserversMutable.put(
                    dynamicActionObserver.actionFilterClass,
                    dispatchersSet
                )
            }
        }
    }
}