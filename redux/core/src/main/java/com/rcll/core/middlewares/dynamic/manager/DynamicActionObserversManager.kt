package com.rcll.core.middlewares.dynamic.manager

import com.rcll.core.api.Action
import com.rcll.core.middlewares.dynamic.DynamicActionObserver

interface DynamicActionObserversManager {
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