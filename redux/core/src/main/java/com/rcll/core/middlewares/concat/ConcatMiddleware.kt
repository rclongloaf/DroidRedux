package com.rcll.core.middlewares.concat

import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware

class ConcatMiddleware<TState : Any>(
    private val concatReducersProvider: ConcatReducersProvider
) : BaseMiddleware<TState>(), ActionConsumer<TState> {

    /**
     * Запускает рекурсивную цепочку обработки экшена.
     * 1. Для каждого [ConcatReducer]-а выполняется [ConcatReducer.reduceBeforeNextReducer].
     * 2. Внутри [ConcatReducer.reduceBeforeNextReducer] может вызваться переданный редюсер,
     * который является [consumeAsync], для рекурсивной обработки новых экшенов.
     * 3. Выполняется [consumeNextAsync] для текущего экшена.
     * 4. Для тех же [ConcatReducer]-ов аналогично выполняется [ConcatReducer.reduceAfterNextReducer]
     *
     * @param state Текущий стейт, который был передан от предыдущего middleware-а, или стейт,
     * который передали вместе с новым экшеном при рекурсивном вызове из [ConcatReducer].
     * @param action Основной экшен, который был передан от предыдущего middleware-а,
     * или новый экшен, который передали из [ConcatReducer]
     *
     * @return Новый стейт, полученный после рекурсивного применения всех экшенов.
     */
    override suspend fun consumeAsync(state: TState, action: Action) {
        consume(state, action)
    }

    override fun consume(state: TState, action: Action) {
        val concatReducers = concatReducersProvider.getConcatReducers()

        concatReducers.forEach { concatReducer ->
            concatReducer.reduceBeforeNextReducer(state, action, this)
        }

        consumeNext(state, action)

        concatReducers.forEach { concatReducer ->
            concatReducer.reduceAfterNextReducer(state, action, this)
        }
    }
}
