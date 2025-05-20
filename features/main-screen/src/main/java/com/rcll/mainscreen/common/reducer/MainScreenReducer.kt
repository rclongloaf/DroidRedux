package com.rcll.mainscreen.common.reducer

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.mainscreen.MutableMainScreen
import com.rcll.mainscreen.init.reducers.reduceInit
import com.rcll.mainscreen.ready.reducers.MainScreenReadyReducer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MainScreenReducer : Reducer<MutableMainScreen>

class MainScreenReducerImpl : MainScreenReducer, KoinComponent {
    private val readyReducer: MainScreenReadyReducer by inject()

    override fun reduce(state: MutableMainScreen, action: Action): MutableMainScreen {
        return when (state) {
            is MutableMainScreen.Init -> reduceInit(state, action)
            is MutableMainScreen.Ready -> readyReducer.reduce(state, action)
        }
    }
}
