package com.rcll.core.middlewares.dynamic.manager

import com.rcll.core.api.Action
import com.rcll.core.middlewares.dynamic.DynamicActionObserver
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlin.reflect.KClass

typealias DynamicActionObserversImmutableMap
        = ImmutableMap<KClass<out Action>, ImmutableSet<DynamicActionObserver<out Action>>>

interface DynamicActionObserversManager {
    val dynamicActionObserversMap: DynamicActionObserversImmutableMap

    /**
     * Добавляет [dynamicActionObserver] в общий список подписок.
     *
     * Вызов обзервера может происходить в любом потоке,
     * поэтому обязательно диспатчим на нужный поток и обрабатываем там.
     */
    fun subscribe(dynamicActionObserver: DynamicActionObserver<out Action>)

    /**
     * Удаляет [dynamicActionObserver] из общего списка подписок.
     */
    fun unsubscribe(dynamicActionObserver: DynamicActionObserver<out Action>)
}