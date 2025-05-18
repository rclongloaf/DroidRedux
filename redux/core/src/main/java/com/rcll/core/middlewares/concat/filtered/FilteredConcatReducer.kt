package com.rcll.core.middlewares.concat.filtered

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * Используется для оптимизирования обработки дополнительных экшенов транзакции за счёт фильтрации
 * по классу экшена. Фильтрация происходит в [ConcatReducersFilter], куда необходимо передать
 * реализацию [FilteredConcatReducer].
 */
abstract class FilteredConcatReducer<TAction : Action> {
    /**
     * Класс экшена, который обрабатывается этим классом.
     */
    abstract val actionFilterClass: KClass<TAction>

    internal fun <TState : Any> reduceBeforeNextReducerUntyped(
        state: TState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        val typedAction = actionFilterClass.cast(action)
        return reduceBeforeNextReducer(state, typedAction, newActionReducer)
    }

    /**
     * Здесь следует применять экшены, которые должны изменить стейт перед
     * выполнением входящего экшена [action].
     *
     * Для входящего экшена [action] reduce будет вызван после отработки этого метода,
     * поэтому обрабатывать его здесь не нужно!
     *
     * @param state Текущее состояние до выполнения входящего экшена [action].
     * @param action Обрабатываемый экшен с типом [actionFilterClass].
     * @param newActionReducer Редюсер для обработки новых экшенов.
     *
     * @return Если применяется новый экшен, то возвращается результат [newActionReducer] с новым экшеном
     * в качестве аргумента, иначе возвращается [state].
     */
    open fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        action: TAction,
        newActionReducer: Reducer<TState>
    ): TState {
        return state
    }

    internal fun <TState : Any> reduceAfterNextReducerUntyped(
        state: TState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        val typedAction = actionFilterClass.cast(action)
        return reduceAfterNextReducer(state, typedAction, newActionReducer)
    }

    /**
     * Здесь следует применять экшены, которые должны изменить стейт после
     * выполнения входящего экшена [action].
     *
     * Для входящего экшена [action] reduce уже был вызван до вызова этой функции,
     * поэтому обрабатывать его здесь не нужно!
     *
     * @param state Текущее состояние после выполнения входящего экшена [action].
     * @param action Обрабатываемый экшен с типом [actionFilterClass].
     * @param newActionReducer Редюсер для обработки новых экшенов.
     *
     * @return Если применяется новый экшен, то возвращается результат [newActionReducer]
     * с новым экшеном в качестве аргумента, иначе возвращается [state].
     */
    open fun <TState : Any> reduceAfterNextReducer(
        state: TState,
        action: TAction,
        newActionReducer: Reducer<TState>
    ): TState {
        return state
    }
}