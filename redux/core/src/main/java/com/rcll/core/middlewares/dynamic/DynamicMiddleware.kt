package com.rcll.core.middlewares.dynamic

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import kotlin.reflect.KClass
import kotlin.reflect.cast

abstract class DynamicMiddleware<TAction : IAction> {
    /**
     * Тип экшена, который обрабатывается этим динмическим мидлваром.
     */
    abstract val actionClassFilter: KClass<TAction>

    fun <TState> reduceBeforeNextReducerUntyped(
        state: TState,
        action: IAction,
        newActionReducer: IReducer<TState>
    ): TState {
        val typedAction = actionClassFilter.cast(action)
        return reduceBeforeNextReducer(state, typedAction, newActionReducer)
    }

    /**
     * Вызывается перед вызовом следующей миддлвары (перед выполнением редьюсера).
     * Здесь следует диспатчить экшены, которые должны применяться ниже по дереву
     * (в дочерних стейтах).
     * @param state Текущее состояние.
     * @param action Обрабатываемый экшен с типом [actionClassFilter].
     * @param newActionReducer Редюсер для обработки новых экшенов. Для action будет вызван reduce
     * после отработки этой функции.
     * @return Если применяется новый экшен, то возвращается результат actionReduce с новым экшеном
     * в качестве аргумента, иначе возвращается [state]
     */
    open fun <TState> reduceBeforeNextReducer(
        state: TState,
        action: TAction,
        newActionReducer: IReducer<TState>
    ): TState {
        return state
    }

    fun <TState> reduceAfterNextReducerUntyped(
        state: TState,
        action: IAction,
        newActionReducer: IReducer<TState>
    ): TState {
        val typedAction = actionClassFilter.cast(action)
        return reduceAfterNextReducer(state, typedAction, newActionReducer)
    }

    /**
     * Вызывается после вызова следующей миддлвары (после выполнения редьюсера).
     * Здесь следует диспатчить экшены, которые должны применяться выше по дереву
     * (в родительском стейте).
     * @param state Текущее состояние.
     * @param action Обрабатываемый экшен с типом [actionClassFilter].
     * @param newActionReducer Редюсер для обработки новых экшенов. Для action уже был вызван reduce
     * перед этой функцией.
     * @return Если применяется новый экшен, то возвращается результат actionReduce с новым экшеном
     * в качестве аргумента, иначе возвращается [state]
     */
    open fun <TState> reduceAfterNextReducer(
        state: TState,
        action: TAction,
        newActionReducer: IReducer<TState>
    ): TState {
        return state
    }
}