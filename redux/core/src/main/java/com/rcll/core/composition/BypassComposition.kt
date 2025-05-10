package com.rcll.core.composition

import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

/**
 * Запускает композицию в передаваемом скоупе.
 * 1. Запущенная композиция поддерживает только Runtime фичи Compose-а.
 * 2. Фичи [Applier]-а не поддерживаются. Используется пустая реализация [StubApplier].
 * 3. Если передать immediate dispatcher,
 * то State-ы будут работать некорректно. Механизм рекомпозиции завязан на глобальный снепшот,
 * и если мы будем пытаться менять стейты внутри композиции,
 * то может вызваться рекомпозиция из неожиданного места и потока.
 * 4. К [recomposeContext] внутри [runBypassComposition] добавляется [BypassFrameClock], который запускает рекомпозицию
 * без дополнительных ожиданий. В [recomposeContext] необходимо передать диспатчер,
 * в котором будет вызываться рекомпозиция.
 * 5. Первый запуск композиции происходит при вызове [runBypassComposition]
 */
fun runBypassComposition(
    scope: CoroutineScope,
    recomposeContext: CoroutineContext,
    compositionContent: @Composable () -> Unit
) {
    val finalRecomposeContext = recomposeContext + BypassFrameClock

    /**
     * effectCoroutineContext - контекст, в котором будут запускаться LaunchedEffect-ы.
     */
    val recomposer = Recomposer(effectCoroutineContext = Dispatchers.Default)
    val composition = Composition(StubApplier(), recomposer)

    scope.launch(context = finalRecomposeContext, start = CoroutineStart.UNDISPATCHED) {
        try {
            recomposer.runRecomposeAndApplyChanges()
        } catch (e: CancellationException) {
            composition.dispose()
        }
    }

    /**
     * Первая композиция происходит внутри этого метода
     */
    composition.setContent(compositionContent)
}