package com.rcll.core.middlewares.dynamic

import com.rcll.core.api.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * @param scope - Скоуп, в котором будет вызываться [observeAction].
 */
abstract class DynamicActionObserver<TAction : Action>(
    private val scope: CoroutineScope
) {
    /**
     * Класс экшена, который обрабатывается этим классом.
     */
    abstract val actionFilterClass: KClass<TAction>

    internal fun observeActionUntyped(action: Action) {
        scope.launch {
            val typedAction = actionFilterClass.cast(action)
            observeAction(typedAction)
        }
    }

    /**
     * Обработка экшена в передаваемом [scope]-е.
     * @param action Обрабатываемый экшен с типом [actionFilterClass].
     */
    abstract suspend fun observeAction(action: TAction)
}