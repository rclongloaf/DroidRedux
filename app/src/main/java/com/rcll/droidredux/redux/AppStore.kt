package com.rcll.droidredux.redux

import com.arkivanov.mvikotlin.core.store.StoreEventType
import com.arkivanov.mvikotlin.core.utils.assertOnMainThread
import com.rcll.core.api.IAction
import com.rcll.core.api.IPatch
import com.rcll.core.base.BaseStore
import com.rcll.core.middlewares.DynamicDispatcherMiddleware
import com.rcll.core.middlewares.ThunkMiddleware
import com.rcll.droidredux.middlewares.TimeTravelStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AppStore(
    private val scope: CoroutineScope,
) : BaseStore<AppState>(
    initialState = AppState(),
    middlewares = listOf(
//        TimeTravelMiddleware<AppState>(),
        ThunkMiddleware(),
        DynamicDispatcherMiddleware()
    ),
    rootReducer = ::reduceApp
), TimeTravelStore<AppState> {
    override val state: AppState
        get() = stateFlow.value

    private val eventsSharedFlow = MutableSharedFlow<TimeTravelStore.Event>()
    override val events: Flow<TimeTravelStore.Event>
        get() = eventsSharedFlow.asSharedFlow()

    private val eventProcessor = EventProcessor()

    override fun dispatch(action: IAction) {
        if (action is IPatch) {
            scope.launch(start = CoroutineStart.UNDISPATCHED) {
                eventsSharedFlow.emit(TimeTravelStore.Event(
                    type = StoreEventType.MESSAGE,
                    value = action,
                    state = state
                ))
            }
        } else {
            super.dispatch(action)
        }
    }

    private fun dispatchSuper(action: IAction) {
        super.dispatch(action)
    }

    override fun restoreState() {
        TODO("Not yet implemented")
    }

    override fun process(
        type: StoreEventType,
        value: Any
    ) {
        eventProcessor.process(type, value)
    }

    override fun debug(
        type: StoreEventType,
        value: Any,
        state: Any
    ) {
        TODO("Not yet implemented")
    }

    private fun changeState(state: AppState) {
        mutableStateFlow.value = state
    }

    private inner class EventProcessor {
        @Suppress("UNCHECKED_CAST")
        fun process(type: StoreEventType, value: Any) {
            assertOnMainThread()

            when (type) {
                StoreEventType.INTENT -> Unit
                StoreEventType.ACTION -> Unit
                StoreEventType.MESSAGE -> processAction(value as IAction)
                StoreEventType.STATE -> changeState(value as AppState)
                StoreEventType.LABEL -> Unit
            }.let {}
        }

        private fun processAction(action: IAction) {
            val previousState = state
//            val newState = reducer.run { previousState.reduce(message) }
//            mutableStateFlow.tryEmit(newState)
            dispatchSuper(action)

            val newState = state
            scope.launch(start = CoroutineStart.UNDISPATCHED) {
                eventsSharedFlow.emit(TimeTravelStore.Event(
                    type = StoreEventType.STATE,
                    value = newState,
                    state = previousState,
                ))
            }
        }
    }
}
