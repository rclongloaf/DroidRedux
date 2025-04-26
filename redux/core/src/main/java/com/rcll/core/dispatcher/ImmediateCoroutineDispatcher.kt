package com.rcll.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

/**
 * Диспатчер, который запускает задачу сразу же, без откладывания на потом,
 * и, следовательно, в том же потоке, из которого эту задачу вызвали.
 * Использовать для работы из разных потоков не безопасно!
 */
object ImmediateCoroutineDispatcher: CoroutineDispatcher() {
    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        return false
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        error("ImmediateCoroutineDispatcher cannot be used for dispatching")
    }

    override fun limitedParallelism(parallelism: Int, name: String?): CoroutineDispatcher {
        throw UnsupportedOperationException()
    }
}