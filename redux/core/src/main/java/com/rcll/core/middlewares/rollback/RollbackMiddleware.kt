package com.rcll.core.middlewares.rollback

import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.error.FatalTransactionException
import com.rcll.core.error.NonFatalTransactionException

class RollbackMiddleware<TState : Any> : BaseMiddleware<TState>() {
    override fun reduce(state: TState, action: Action): TState {
        var newState = state
        try {
            newState = reduceNext(newState, action)
        } catch (e: FatalTransactionException) {
            // log exception and throw it
            /**
             * Ошибки, которые могут нарушить работу приложения.
             * Например не смогли применить экшен завершения асинхронной задачи.
             * Повторно этот экшен уже не отправится и мы можем застрять, поэтому крашим.
             */
            throw e
        } catch (e: NonFatalTransactionException) {
            // log exception and ignore it
            /**
             * Ошибки, которые не влияют на работу приложения.
             * Например спам экшенами от кликов пользователся, или просто протухший экшен,
             * когда стейт уже закрылся и некому принимать этот экшен.
             * Пользователь может повторно кликнуть на кнопку и попытаться обработать экшен,
             * поэтому может проигнорировать.
             * (Но в основном это ошибка при получение протухших экшенов)
             */
        } catch (e: Throwable) {
            // log exception and throw
            throw e
        }

        return newState
    }
}