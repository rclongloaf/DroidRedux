package com.rcll.mainscreen.common.reducer

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.init.reducers.reduceInit
import com.rcll.mainscreen.ready.reducers.MainScreenReadyReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MainScreenReducer : Reducer<MainScreen>

class MainScreenReducerImpl : MainScreenReducer, KoinComponent {
    private val readyReducer: MainScreenReadyReducer by inject()

    override fun reduce(state: MainScreen, action: Action): MainScreen {
        return when (state) {
            is MainScreen.Init -> reduceInit(state, action)
            is MainScreen.Ready -> readyReducer.reduce(state, action)
        }
    }
}
