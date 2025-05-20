package com.rcll.mainscreen.common.reducer.concat

import com.rcll.core.api.Action
import com.rcll.core.middlewares.concat.ActionConsumer
import com.rcll.core.middlewares.concat.SubConcatReducer
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.ready.actions.CounterAction
import com.rcll.timerservice.active.actions.TimerEvent

interface MainScreenSubConcatReducer : SubConcatReducer<MainScreen>

internal class MainScreenSubConcatReducerImpl : MainScreenSubConcatReducer {
    override fun <TState : Any> reduceAfterNextReducer(
        state: TState,
        subState: MainScreen,
        action: Action,
        newActionConsumer: ActionConsumer<TState>
    ) {
        if (subState !is MainScreen.Ready ||
            action !is TimerEvent ||
            action.key != subState.timer.key
        ) return

        newActionConsumer.consume(state, CounterAction.Increase)
    }
}