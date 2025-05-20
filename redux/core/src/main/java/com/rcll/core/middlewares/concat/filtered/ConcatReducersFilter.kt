package com.rcll.core.middlewares.concat.filtered

import com.rcll.core.api.Action
import com.rcll.core.middlewares.concat.ActionConsumer
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
        newActionConsumer: ActionConsumer<TState>
    ) {
        val filteredConcatReducers = filteredConcatReducersProvider.getConcatReducers(action::class)

        filteredConcatReducers.forEach { concatReducer ->
            concatReducer.reduceBeforeNextReducerUntyped(state, action, newActionConsumer)
        }
    }

    override fun <TState : Any> reduceAfterNextReducer(
        state: TState,
        action: Action,
        newActionConsumer: ActionConsumer<TState>
    ) {
        val filteredConcatReducers = filteredConcatReducersProvider.getConcatReducers(action::class)

        filteredConcatReducers.forEach { concatReducer ->
            concatReducer.reduceAfterNextReducerUntyped(state, action, newActionConsumer)
        }
    }
}