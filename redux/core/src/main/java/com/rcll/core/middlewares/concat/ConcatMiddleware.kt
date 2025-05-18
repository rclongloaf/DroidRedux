package com.rcll.core.middlewares.concat

import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware

class ConcatMiddleware<TState : Any>(
    private val concatReducersProvider: ConcatReducersProvider
) : BaseMiddleware<TState>() {

    /**
     * Запускает рекурсивную цепочку обработки экшена.
     * 1. Для каждого [ConcatReducer]-а выполняется [ConcatReducer.reduceBeforeNextReducer].
     * 2. Внутри [ConcatReducer.reduceBeforeNextReducer] может вызваться переданный редюсер,
     * который является [reduce], для рекурсивной обработки новых экшенов.
     * 3. Выполняется [reduceNext] для текущего экшена.
     * 4. Для тех же [ConcatReducer]-ов аналогично выполняется [ConcatReducer.reduceAfterNextReducer]
     *
     * @param state Текущий стейт, который был передан от предыдущего middleware-а, или стейт,
     * который передали вместе с новым экшеном при рекурсивном вызове из [ConcatReducer].
     * @param action Основной экшен, который был передан от предыдущего middleware-а,
     * или новый экшен, который передали из [ConcatReducer]
     *
     * @return Новый стейт, полученный после рекурсивного применения всех экшенов.
     */
    override fun reduce(state: TState, action: Action): TState {
        var newState = state

        val concatReducers = concatReducersProvider.getConcatReducers()

        concatReducers.forEach { concatReducer ->
            newState = concatReducer.reduceBeforeNextReducer(newState, action, this)
        }

        newState = reduceNext(newState, action)

        concatReducers.forEach { concatReducer ->
            newState = concatReducer.reduceAfterNextReducer(newState, action, this)
        }

        return newState
    }
}
