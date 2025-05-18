package com.rcll.core.middlewares.concat.filtered

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.core.middlewares.concat.ConcatReducer

/**
 * Реализация, которая фильтрует [FilteredConcatReducer]-ы по классу экшена и делегирует обработку
 * дополнительных экшенов транзакции отфильтрованным [FilteredConcatReducer]-ов.
 */
class ConcatReducersFilter(
    private val filteredConcatReducersProvider: FilteredConcatReducersProvider
) : ConcatReducer {

    override fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        var newState = state

        val filteredConcatReducers = filteredConcatReducersProvider.getConcatReducers(action::class)

        filteredConcatReducers.forEach { concatReducer ->
            newState = concatReducer.reduceBeforeNextReducerUntyped(state, action, newActionReducer)
        }

        return newState
    }

    override fun <TState : Any> reduceAfterNextReducer(
        state: TState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        var newState = state

        val filteredConcatReducers = filteredConcatReducersProvider.getConcatReducers(action::class)

        filteredConcatReducers.forEach { concatReducer ->
            newState = concatReducer.reduceAfterNextReducerUntyped(state, action, newActionReducer)
        }

        return newState
    }
}