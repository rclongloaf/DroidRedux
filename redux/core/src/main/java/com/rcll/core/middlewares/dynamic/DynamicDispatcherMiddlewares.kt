package com.rcll.core.middlewares.dynamic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.compositionLocalOf
import com.rcll.core.api.IAction
import com.rcll.core.base.BaseMiddleware
import com.rcll.core.middlewares.dynamic.holder.DynamicMiddlewaresHolder
import com.rcll.core.middlewares.dynamic.holder.DynamicMiddlewaresImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentHashSetOf
import kotlin.reflect.KClass


class DynamicDispatcherMiddlewares<TState : Any>(
    private val dynamicMiddlewaresHolder: DynamicMiddlewaresHolder
) : BaseMiddleware<TState>() {
    private val actionsScope = ActionsChainScope()

    /**
     * Запускает цепочку обработки экшена.
     * 1. Открывается скоуп экшена, для которого сохраняется текущая копия списка динамических мидлвар.
     * 2. Для каждого динамического мидлвара по ключу типа экшена выполняется reduceBeforeNextReducer.
     * 3. Внутри reduceBeforeNextReducer может вызваться переданный редюсер, т.е. этот метод,
     * с новым экшеном и тем же списком динамичных мидлвар,
     * т.к. скоуп и сохраняется для всех вложенных экшенов.
     * 4. Выполняется reduceNext для текущего экшена.
     * 5. Для каждого динамического мидлвара по ключу типа экшена выполняется reduceAfterNextReducer
     * аналогично reduceBeforeNextReducer.
     */
    override fun reduce(state: TState, action: IAction): TState {
        var newState = state

        actionsScope.with(
            actionClassFilter = action::class,
            latestDynamicMiddlewares = dynamicMiddlewaresHolder.dynamicMiddlewares
        ) { middlewares ->
            middlewares.forEach { dynamicMiddleware ->
                newState = dynamicMiddleware.reduceBeforeNextReducerUntyped(newState, action, this)
            }

            newState = reduceNext(newState, action)

            middlewares.forEach { dynamicMiddleware ->
                newState = dynamicMiddleware.reduceAfterNextReducerUntyped(newState, action, this)
            }
        }

        return newState
    }

    inner class ActionsChainScope {
        private var localDynamicMiddlewares: DynamicMiddlewaresImmutableMap? = null
        private var actionsDepth = 0

        /**
         * Использование этого метода гарантирует, что будет использован один и тот же список
         * динамических миддлвар для всей вложенной цепочки экшенов.
         * @param actionClassFilter тип экшена, которому будут отфильтрованы миддлвары.
         * @param latestDynamicMiddlewares коллекция миддлвар, которая будет сохранена в скоупе
         * при вызове первого экшена в цепочке и использована в остальных вложенных
         * экшенах независимо от значений [latestDynamicMiddlewares] на момент их вызова.
         */
        fun with(
            actionClassFilter: KClass<out IAction>,
            latestDynamicMiddlewares: DynamicMiddlewaresImmutableMap,
            block: (middlewares: ImmutableSet<DynamicMiddleware<out IAction>>) -> Unit
        ) {
            if (actionsDepth == 0) {
                localDynamicMiddlewares = latestDynamicMiddlewares
            }
            actionsDepth++

            localDynamicMiddlewares?.let { middlewaresMap ->
                val filteredMiddlewares = middlewaresMap[actionClassFilter]
                    ?: persistentHashSetOf<DynamicMiddleware<IAction>>()
                block(filteredMiddlewares)
            } ?: error("ActionsChainScope is not supported multi threads")

            actionsDepth--
            if (actionsDepth == 0) {
                localDynamicMiddlewares = null
            }
        }
    }
}

val LocalDynamicMiddlewareManager =
    compositionLocalOf<DynamicMiddlewaresHolder> { error("No class provided") }

@Composable
inline fun DynamicMiddlewareManagerProvider(
    manager: DynamicMiddlewaresHolder,
    crossinline content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDynamicMiddlewareManager provides manager) {
        content()
    }
}


@Composable
@NonRestartableComposable
fun <TAction : IAction> WithDynamicMiddleware(
    key: Any,
    dispatcher: DynamicMiddleware<TAction>
) {
    val manager = LocalDynamicMiddlewareManager.current

    DisposableEffect(key, dispatcher) {
        manager.add(dispatcher)

        onDispose {
            manager.remove(dispatcher)
        }
    }
}
