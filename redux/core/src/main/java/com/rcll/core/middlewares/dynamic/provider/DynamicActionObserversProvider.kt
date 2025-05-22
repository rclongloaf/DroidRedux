package com.rcll.core.middlewares.dynamic.provider

import com.rcll.core.api.Action
import com.rcll.core.middlewares.dynamic.DynamicActionObserver
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlin.reflect.KClass

typealias DynamicActionObserversImmutableMap
        = ImmutableMap<KClass<out Action>, ImmutableSet<DynamicActionObserver<out Action>>>

interface DynamicActionObserversProvider {
    val dynamicActionObserversMap: DynamicActionObserversImmutableMap

    fun add(dynamicActionObserver: DynamicActionObserver<out Action>)
    fun remove(dynamicActionObserver: DynamicActionObserver<out Action>)
}