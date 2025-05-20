package com.rcll.core.middlewares.dynamic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.compositionLocalOf
import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.middlewares.dynamic.manager.DynamicActionObserversManager
import kotlinx.collections.immutable.persistentListOf

class DynamicMiddleware<TState : Any>(
    private val dynamicActionObserversManager: DynamicActionObserversManager
) : BaseMiddleware<TState>() {

    /**
     * Выполняет отправку экшенов в динамически изменяемый список
     */
    override suspend fun consumeAsync(state: TState, action: Action) {
        notifyObservers(action)
        consumeNextAsync(state, action)
    }

    override fun consume(state: TState, action: Action) {
        notifyObservers(action)
        consumeNext(state, action)
    }

    private fun notifyObservers(action: Action) {
        val dynamicMiddlewares = dynamicActionObserversManager
            .dynamicActionObserversMap[action::class]
            ?: persistentListOf()

        dynamicMiddlewares.forEach { dynamicMiddleware ->
            dynamicMiddleware.observeActionUntyped(action)
        }
    }
}

val LocalDynamicActionObserversManager =
    compositionLocalOf<DynamicActionObserversManager> { error("No class provided") }

@Composable
inline fun DynamicMiddlewareManagerProvider(
    manager: DynamicActionObserversManager,
    crossinline content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDynamicActionObserversManager provides manager) {
        content()
    }
}


@Composable
@NonRestartableComposable
fun <TAction : Action> WithDynamicActionObserver(
    key: Any,
    dynamicActionObserver: DynamicActionObserver<TAction>
) {
    val manager = LocalDynamicActionObserversManager.current

    DisposableEffect(key, dynamicActionObserver) {
        manager.subscribe(dynamicActionObserver)

        onDispose {
            manager.unsubscribe(dynamicActionObserver)
        }
    }
}
