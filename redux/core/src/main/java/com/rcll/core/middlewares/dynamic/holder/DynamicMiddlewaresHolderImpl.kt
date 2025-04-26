package com.rcll.core.middlewares.dynamic.holder

import com.rcll.core.api.IAction
import com.rcll.core.middlewares.dynamic.DynamicMiddleware
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentSetOf
import kotlin.reflect.KClass

typealias DynamicMiddlewaresMap = PersistentMap<KClass<out IAction>, PersistentSet<DynamicMiddleware<out IAction>>>
typealias DynamicMiddlewaresImmutableMap = ImmutableMap<KClass<out IAction>, ImmutableSet<DynamicMiddleware<out IAction>>>

class DynamicMiddlewaresHolderImpl : DynamicMiddlewaresHolder {
    private var dynamicMiddlewaresMutable: DynamicMiddlewaresMap = persistentHashMapOf()

    override val dynamicMiddlewares: DynamicMiddlewaresImmutableMap
        get() = dynamicMiddlewaresMutable

    override fun add(dynamicMiddleware: DynamicMiddleware<out IAction>) {
        synchronized(this) {
            var dispatchersSet = dynamicMiddlewaresMutable[dynamicMiddleware.actionClassFilter]
            if (dispatchersSet == null) {
                dispatchersSet = persistentSetOf(dynamicMiddleware)
            } else {
                if (dispatchersSet.contains(dynamicMiddleware)) {
                    throw IllegalStateException("Dynamic dispatcher already exists")
                }
                dispatchersSet = dispatchersSet.add(dynamicMiddleware)
            }
            dynamicMiddlewaresMutable =
                dynamicMiddlewaresMutable.put(dynamicMiddleware.actionClassFilter, dispatchersSet)
        }
    }

    override fun remove(dynamicMiddleware: DynamicMiddleware<out IAction>) {
        synchronized(this) {
            var dispatchersSet = dynamicMiddlewaresMutable[dynamicMiddleware.actionClassFilter]
            if (dispatchersSet == null || !dispatchersSet.contains(dynamicMiddleware)) {
                throw IllegalStateException("Dynamic dispatcher not found")
            }
            if (dispatchersSet.size == 1) {
                dynamicMiddlewaresMutable =
                    dynamicMiddlewaresMutable.remove(dynamicMiddleware.actionClassFilter)
            } else {
                dispatchersSet = dispatchersSet.remove(dynamicMiddleware)
                dynamicMiddlewaresMutable = dynamicMiddlewaresMutable.put(
                    dynamicMiddleware.actionClassFilter,
                    dispatchersSet
                )
            }
        }
    }
}