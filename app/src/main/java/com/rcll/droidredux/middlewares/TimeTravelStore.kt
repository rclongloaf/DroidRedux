package com.rcll.droidredux.middlewares

import com.arkivanov.mvikotlin.core.annotations.MainThread
import com.arkivanov.mvikotlin.core.store.StoreEventType
import kotlinx.coroutines.flow.Flow

interface TimeTravelStore<out State : Any> {
    val state: State

    val events: Flow<Event>

    @MainThread
    fun restoreState()

    @MainThread
    fun process(type: StoreEventType, value: Any)

    @MainThread
    fun debug(type: StoreEventType, value: Any, state: Any)

    data class Event(
        val type: StoreEventType,
        val value: Any,
        val state: Any
    )
}
