package com.rcll.core.middlewares.rollback

import androidx.compose.runtime.snapshots.Snapshot
import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.error.FatalTransactionException
import com.rcll.core.error.TransactionException

class RollbackMiddleware<TState : Any> : BaseMiddleware<TState>() {
    override suspend fun consumeAsync(state: TState, action: Action) {
        try {
            Snapshot.withMutableSnapshot {
                consumeNextAsync(state, action)
            }
        } catch (e: FatalTransactionException) {
            // todo log exception and maybe throw it
            throw e
        } catch (e: TransactionException) {
            // todo log exception and ignore it
        } catch (e: Throwable) {
            // todo log exception and throw
            throw e
        }
    }

    override fun consume(state: TState, action: Action) {
        try {
            Snapshot.withMutableSnapshot {
                consume(state, action)
            }
        } catch (e: FatalTransactionException) {
            // todo log exception and maybe throw it
            throw e
        } catch (e: TransactionException) {
            // todo log exception and ignore it
        } catch (e: Throwable) {
            // todo log exception and throw
            throw e
        }
    }
}