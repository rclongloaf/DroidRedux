package com.rcll.core.middlewares.concat

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer

/**
 * Используется для делегирования обработки дополнительных экшенов транзакции дочерним состояниям.
 */
interface SubConcatReducer<TSubState> {

    /**
     * Здесь следует применять экшены, которые должны изменить стейт перед
     * выполнением входящего экшена [action].
     *
     * Для входящего экшена [action] reduce будет вызван после отработки этого метода,
     * поэтому обрабатывать его здесь не нужно!
     *
     * @param state Текущее состояние до выполнения входящего экшена [action].
     * @param subState Состояние поддерева [state]-а.
     * @param action Обрабатываемый экшен.
     * @param newActionReducer Редюсер для обработки новых экшенов.
     *
     * @return Если применяется новый экшен, то возвращается результат [newActionReducer]
     * с новым экшеном в качестве аргумента, иначе возвращается [state].
     */
    fun <TState : Any> reduceBeforeNextReducer(
        state: TState,
        subState: TSubState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        return state
    }

    /**
     * Здесь следует применять экшены, которые должны изменить стейт после
     * выполнения входящего экшена [action].
     *
     * Для входящего экшена [action] reduce уже был вызван до вызова этой функции,
     * поэтому обрабатывать его здесь не нужно!
     *
     * @param state Текущее состояние после выполнения входящего экшена [action].
     * @param subState Состояние поддерева [state]-а.
     * @param action Обрабатываемый экшен.
     * @param newActionReducer Редюсер для обработки новых экшенов.
     *
     * @return Если применяется новый экшен, то возвращается результат [newActionReducer]
     * с новым экшеном в качестве аргумента, иначе возвращается [state].
     */
    fun <TState : Any> reduceAfterNextReducer(
        state: TState,
        subState: TSubState,
        action: Action,
        newActionReducer: Reducer<TState>
    ): TState {
        return state
    }
}