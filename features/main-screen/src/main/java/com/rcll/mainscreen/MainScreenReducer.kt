package com.rcll.mainscreen

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import com.rcll.mainscreen.init.reducers.reduceInit
import com.rcll.mainscreen.ready.reducers.MainScreenReadyReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MainScreenReducer : IReducer<MainScreen>

class MainScreenReducerImpl : MainScreenReducer, KoinComponent {
    private val readyReducer: MainScreenReadyReducer by inject()

    override fun reduce(state: MainScreen, action: IAction): MainScreen {
        return when (state) {
            is MainScreen.Init -> reduceInit(state, action)
            is MainScreen.Ready -> readyReducer.reduce(state, action)
        }
    }
}
