//package com.rcll.droidredux.middlewares
//
//import com.arkivanov.mvikotlin.core.rx.Disposable
//import com.arkivanov.mvikotlin.core.rx.Observer
//import com.arkivanov.mvikotlin.core.store.StoreEventType
//import com.rcll.core.api.IAction
//import com.rcll.core.api.IStore
//import com.rcll.core.base.BaseMiddleware
//
//class TimeTravelMiddleware<TState : Any> : BaseMiddleware() {
//    private var timeTravelStore: TimeTravelStore<IAction, TState, Any>? = null
//
//    override fun dispatch(action: IAction) {
//        timeTravelStore?.accept(action)
//
//        next?.dispatch(action)
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun setStore(store: IStore<*>) {
//        super.setStore(store)
//
//        timeTravelStore?.dispose()
//
//        timeTravelStore = object : TimeTravelStore<IAction, TState, Any> {
//            override fun events(observer: Observer<TimeTravelStore.Event>): Disposable {
//                TODO("Not yet implemented")
//            }
//
//            override fun restoreState() {
//                TODO("Not yet implemented")
//            }
//
//            override fun process(
//                type: StoreEventType,
//                value: Any
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun debug(
//                type: StoreEventType,
//                value: Any,
//                state: Any
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override val state: TState
//                get() = TODO("Not yet implemented")
//            override val isDisposed: Boolean
//                get() = TODO("Not yet implemented")
//
//            override fun states(observer: Observer<TState>): Disposable {
//                TODO("Not yet implemented")
//            }
//
//            override fun labels(observer: Observer<Any>): Disposable {
//                TODO("Not yet implemented")
//            }
//
//            override fun accept(intent: IAction) {
//                TODO("Not yet implemented")
//            }
//
//            override fun init() {
//                TODO("Not yet implemented")
//            }
//
//            override fun dispose() {
//                TODO("Not yet implemented")
//            }
//
//        }
//    }
//}
