//package com.rcll.droidredux.middlewares
//
//import com.arkivanov.mvikotlin.core.rx.Disposable
//import com.arkivanov.mvikotlin.core.rx.Observer
//import com.arkivanov.mvikotlin.core.rx.internal.BehaviorSubject
//import com.arkivanov.mvikotlin.core.rx.internal.PublishSubject
//import com.arkivanov.mvikotlin.core.store.Bootstrapper
//import com.arkivanov.mvikotlin.core.store.Executor
//import com.arkivanov.mvikotlin.core.store.Reducer
//import com.arkivanov.mvikotlin.core.store.StoreEventType
//import com.arkivanov.mvikotlin.core.utils.assertOnMainThread
//import com.rcll.droidredux.middlewares.TimeTravelStore.Event
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers.Main
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.SupervisorJob
//import kotlinx.coroutines.cancel
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onCompletion
//import kotlinx.coroutines.flow.onEach
//import kotlin.concurrent.Volatile
//
//internal class TimeTravelStoreImpl<in Intent : Any, in Action : Any, in Message : Any, out State : Any, Label : Any>(
//    initialState: State,
//    private val executorFactory: () -> Executor<Intent, Action, State, Message, Label>,
//    private val reducer: Reducer<State, Message>,
//    private val onInit: (TimeTravelStore<Intent, State, Label>) -> Unit = {},
//) : TimeTravelStore<State> {
//    private val scope = CoroutineScope(SupervisorJob() + Main)
//
//    private val executor = executorFactory()
//    private var internalState = initialState
//    private val stateSubject = MutableStateFlow(initialState)
//    override val state: State get() = stateSubject.value
//    private val eventSubjects = StoreEventType.entries.associateWith { MutableSharedFlow<Event>() }
//    private var debuggingExecutor: Executor<*, *, *, *, *>? = null
//    private val eventProcessor = EventProcessor()
//    private val eventDebugger = EventDebugger()
//    private var isInitialized = false
//
//    override fun events(observer: Observer<Event>): Disposable {
//        val disposables = eventSubjects.values.map { it
//            .onEach { observer.onNext(it) }
//            .onCompletion { observer.onComplete() }
//            .launchIn(scope)
//        }
//
//        return Disposable { disposables.forEach(Job::cancel) }
//    }
//
////    override fun accept(intent: Intent) {
////        assertOnMainThread()
////
////        doIfNotDisposed {
////            onEvent(StoreEventType.INTENT, intent, state)
////        }
////    }
//
//    override fun dispose() {
//        assertOnMainThread()
//
//        doIfNotDisposed {
//            debuggingExecutor?.dispose()
//            debuggingExecutor = null
//            executor.dispose()
//            scope.cancel()
//        }
//    }
//
//    fun init() {
//        assertOnMainThread()
//
//        if (isInitialized) {
//            return
//        }
//
//        isInitialized = true
//
//        onInit(this)
//
//        executor.init(
//            object : Executor.Callbacks<State, Message, Action, Label> {
//                override val state: State get() = internalState
//
//                override fun onMessage(message: Message) {
//                    onEvent(StoreEventType.MESSAGE, message, state)
//                }
//
//                override fun onAction(action: Action) {
//                    onEvent(StoreEventType.ACTION, action, state)
//                }
//
//                override fun onLabel(label: Label) {
//                    onEvent(StoreEventType.LABEL, label, state)
//                }
//            }
//        )
//
////        bootstrapper?.init {
////            onEvent(StoreEventType.ACTION, it, state)
////        }
//    }
//
//    override fun restoreState() {
//        assertOnMainThread()
//
//        doIfNotDisposed {
//            changeState(internalState)
//        }
//    }
//
//    override fun process(type: StoreEventType, value: Any) {
//        eventProcessor.process(type, value)
//    }
//
//    override fun debug(type: StoreEventType, value: Any, state: Any) {
//        eventDebugger.debug(type, value, state)
//    }
//
//    private fun onEvent(type: StoreEventType, value: Any, state: State) {
//        assertOnMainThread()
//
//        doIfNotDisposed {
//            eventSubjects.getValue(type).tryEmit(Event(type = type, value = value, state = state))
//        }
//    }
//
//    private fun changeState(state: State) {
//        stateSubject.tryEmit(state)
//    }
//
//    private inline fun doIfNotDisposed(block: () -> Unit) {
////        if (!isDisposed) {
//            block()
////        }
//    }
//
//    private inner class EventProcessor {
//        @Suppress("UNCHECKED_CAST")
//        fun process(type: StoreEventType, value: Any) {
//            assertOnMainThread()
//
//            doIfNotDisposed {
//                when (type) {
//                    StoreEventType.INTENT -> executor.executeIntent(value as Intent)
//                    StoreEventType.ACTION -> executor.executeAction(value as Action)
//                    StoreEventType.MESSAGE -> processMessage(value as Message)
//                    StoreEventType.STATE -> changeState(value as State)
//                    StoreEventType.LABEL -> Unit //labelSubject.onNext(value as Label)
//                }.let {}
//            }
//        }
//
//        private fun processMessage(message: Message) {
//            val previousState = internalState
//            val newState = reducer.run { previousState.reduce(message) }
//            internalState = newState
//
//            onEvent(StoreEventType.STATE, newState, previousState)
//        }
//    }
//
//    private inner class EventDebugger {
//        @Suppress("UNCHECKED_CAST")
//        fun debug(type: StoreEventType, value: Any, state: Any) {
//            assertOnMainThread()
//
//            doIfNotDisposed {
//                when (type) {
//                    StoreEventType.INTENT -> debugIntent(value as Intent, state as State)
//                    StoreEventType.ACTION -> debugAction(value as Action, state as State)
//                    StoreEventType.MESSAGE -> debugMessage(value as Message, state as State)
//                    StoreEventType.STATE -> throw IllegalArgumentException("Can't debug event of type: $type")
//                    StoreEventType.LABEL -> debugLabel(value as Label)
//                }.let {}
//            }
//        }
//
//        private fun debugIntent(intent: Intent, initialState: State) {
//            debugExecutor(initialState) {
//                executeIntent(intent)
//            }
//        }
//
//        private fun debugAction(action: Action, initialState: State) {
//            debugExecutor(initialState) {
//                executeAction(action)
//            }
//        }
//
//        private fun debugExecutor(initialState: State, execute: Executor<Intent, Action, State, Message, Label>.() -> Unit) {
//            val executor =
//                executorFactory().apply {
//                    init(DebugExecutorCallbacks(initialState, reducer))
//                    execute()
//                }
//
//            debuggingExecutor?.dispose()
//            debuggingExecutor = executor
//        }
//
//        private fun debugMessage(message: Message, initialState: State) {
//            with(reducer) {
//                initialState.reduce(message)
//            }
//        }
//
//        private fun debugLabel(label: Label) {
////            labelSubject.onNext(label)
//        }
//    }
//
//
//    private class DebugExecutorCallbacks<State, in Message, in Action, in Label>(
//        initialState: State,
//        private val reducer: Reducer<State, Message>,
//    ) : Executor.Callbacks<State, Message, Action, Label> {
//        @Volatile
//        override var state: State = initialState
//            private set
//
//        override fun onMessage(message: Message) {
//            assertOnMainThread()
//            state = reducer.run { state.reduce(message) }
//        }
//
//        override fun onAction(action: Action) {
//            assertOnMainThread()
//        }
//
//        override fun onLabel(label: Label) {
//            assertOnMainThread()
//        }
//    }
//}
